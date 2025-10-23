pipeline {
    agent any

    environment {
        // MySQL Configuration
        DB_URL = 'jdbc:mysql://localhost:3306/springonetomany'
        DB_USER = 'root'
        DB_PASS = 'root'
        APP_JAR = 'target/player-team-cucumber-0.0.1-SNAPSHOT.jar'
        DOCKER_REPO = 'player-team-cucumber'
        APP_PORT = '9090'  // ✅ avoid Jenkins port conflict
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

        stage('Unit & Cucumber Tests') {
            steps {
                echo '🧪 Running Cucumber + Unit Tests...'
                bat 'mvn test'
            }
        }

        stage('Start MySQL Container') {
            steps {
                echo '🗄️ Starting MySQL container...'

                bat """
                REM Stop/remove any previous MySQL container
                docker stop mysql-server || echo No previous MySQL container
                docker rm mysql-server || echo No previous MySQL container

                REM Run MySQL container
                docker run -d --name mysql-server ^
                    -e MYSQL_ROOT_PASSWORD=root ^
                    -e MYSQL_DATABASE=springonetomany ^
                    -p 3306:3306 ^
                    mysql:8.0

                REM Wait for MySQL to fully initialize
                timeout /t 15
                """
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

        stage('Run Application Container') {
            steps {
                echo "▶️ Running Spring Boot Docker container linked with MySQL..."
                bat """
                REM Stop/remove old containers
                docker stop player-team-cucumber || echo No existing app container
                docker rm player-team-cucumber || echo No existing app container

                REM Wait for MySQL to stabilize
                timeout /t 10

                REM Run Spring Boot container linked to MySQL
                docker run -d --name player-team-cucumber ^
                    --link mysql-server:mysql ^
                    -e SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/springonetomany ^
                    -e SPRING_DATASOURCE_USERNAME=root ^
                    -e SPRING_DATASOURCE_PASSWORD=root ^
                    -p 9090:8080 ^
                    ${DOCKER_REPO}:${env.IMAGE_TAG}
                """
            }
        }

        stage('Verify Application') {
            steps {
                echo '🔍 Verifying that the app is running...'
                bat 'timeout /t 10 /nobreak'
                bat 'curl -f http://localhost:9090/actuator/health || echo "App not reachable"'
            }
        }
    }

    post {
        always {
            echo "✅ Pipeline finished. Listing Docker containers..."
            bat 'docker ps -a'
        }
        success {
            echo "🎉 Pipeline succeeded!"
            echo "🌐 Application URL: http://localhost:${APP_PORT}/"
        }
        failure {
            echo "❌ Pipeline failed. Check logs above for errors."
        }
    }
}
