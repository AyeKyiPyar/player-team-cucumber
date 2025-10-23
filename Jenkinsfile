pipeline {
    agent any

    environment {
        REPO_URL = 'https://github.com/AyeKyiPyar/player-team-cucumber.git'
        DOCKER_COMPOSE_FILE = 'docker-compose.yml'
    }

    stages {
        stage('Checkout') {
            steps {
                echo '📦 Cloning repository...'
                git branch: 'main', url: "${REPO_URL}"
            }
        }

        stage('Build') {
            steps {
                echo '🏗️ Building Maven project...'
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Run Cucumber Tests') {
            steps {
                echo '🧪 Running Cucumber tests...'
                bat 'mvn test'
            }
        }

        stage('Docker Compose Up') {
            steps {
                echo '🐳 Starting MySQL and Spring Boot app via Docker Compose...'
                // Stop and remove old containers
                bat "docker-compose -f ${DOCKER_COMPOSE_FILE} down"
                // Build images and start containers
                bat "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d --build"
                // Wait a few seconds for MySQL to initialize
                bat "timeout /t 15"
            }
        }

        stage('Verify Deployment') {
            steps {
                echo '🔍 Verifying Spring Boot app is running...'
                bat 'curl -f http://localhost:7074/actuator/health || echo "App not reachable"'
            }
        }
    }

    post {
        always {
            echo '✅ Pipeline finished. Current Docker containers:'
            bat 'docker ps -a'
        }
        success {
            echo "🎉 Pipeline succeeded! App running at http://localhost:7074/"
        }
        failure {
            echo '❌ Pipeline failed. Check logs above.'
        }
    }
}
