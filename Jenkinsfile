pipeline {
    agent any

    environment {
        // MySQL Configuration
        DB_URL = 'jdbc:mysql://localhost:3306/springonetomany'
        DB_USER = 'root'
        DB_PASS = 'root'
        APP_JAR = 'target/player-team-cucumber-0.0.1-SNAPSHOT.jar'
        DOCKER_REPO = 'player-team-cucumber'
        APP_PORT = '9090'  // ✅ Changed from 8080 to 9090 to avoid Jenkins conflict
    }

    stages {

        stage('Checkout') {
            steps {
                echo '📦 Cloning repository...'
                git branch: 'main', url: 'https://github.com/AyeKyiPyar/player-team-cucumber-demo.git'
            }
        }

        stage('Build') {
            steps {
                echo '🏗️ Building the project...'
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo '🧪 Running Cucumber Tests...'
                bat 'mvn test'
            }
        }

        stage('Database Setup') {
            steps {
                echo '🗄️ Ensuring MySQL is ready...'
                bat """
                    "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysql.exe" -u%DB_USER% -p%DB_PASS% -e "CREATE DATABASE IF NOT EXISTS springonetomany;"
                """
            }
        }

       stage('Deploy') {
		    steps {
		        echo '🚀 Deploying Spring Boot App on port 9090...'
		        bat '''
		        echo Checking if port 9090 is in use...
		        for /f "tokens=5" %%a in ('netstat -ano ^| findstr :9090') do (
		            echo Killing process %%a using port 9090...
		            taskkill /PID %%a /F
		        )
		        echo Port 9090 cleanup done (or not needed).
		        exit /b 0
		        '''
		    }
		}


        stage('Build Docker Image') {
            steps {
                script {
                    echo "🐳 Building Docker image..."
                    def imageTag = "${env.BUILD_NUMBER}"
                    bat "docker build -t ${DOCKER_REPO}:${imageTag} ."
                    bat "docker tag ${DOCKER_REPO}:${imageTag} ${DOCKER_REPO}:latest"
                    env.IMAGE_TAG = imageTag
                }
            }
        }

        stage('Run Docker Container') {
            steps {
                echo "▶️ Running Docker container on host port 8081..."
                bat """
                    docker stop player-team-cucumber || true
                    docker rm player-team-cucumber || true
                    docker run -d --name player-team-cucumber -p 8081:8080 ${DOCKER_REPO}:${env.IMAGE_TAG}
                """
            }
        }
    }

    post {
        always {
            echo "✅ Pipeline finished."
        }
        success {
            echo "🎉 Pipeline succeeded!"
            echo "🌐 App running on: http://localhost:${APP_PORT}/"
            echo "🐋 Docker container available at: http://localhost:8081/"
        }
        failure {
            echo "❌ Pipeline failed. Check logs above."
        }
    }
}
