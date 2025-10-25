pipeline {
    agent any

    environment {
        REPO_URL = 'https://github.com/AyeKyiPyar/player-team-cucumber.git'
        DOCKER_COMPOSE_FILE = 'docker-compose.yml'
        APP_CONTAINER = 'player-team-cucumber-app'
        APP_JAR = 'target/player-team-cucumber-0.0.1-SNAPSHOT.jar'
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

        stage('Start Test Environment') {
            steps {
                echo '⚙️ Starting MySQL container for Cucumber tests...'
                bat "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d mysql_db"
                bat 'powershell -Command "Start-Sleep -Seconds 20"' // wait for MySQL
            }
        }

        stage('Run Cucumber Tests') {
            steps {
                echo '🧪 Running Cucumber tests inside container...'
                // Run tests outside the Spring Boot app container
                 bat 'mvn test -Dcucumber.options="--plugin json:target/cucumber.json"'
            }
        }

        stage('Publish Cucumber Results') {
            steps {
                echo '📊 Publishing Cucumber results...'
                // Use HTML publisher instead of 'cucumber' step
                publishHTML(target: [
                    reportDir: 'target/cucumber-reports',
                    reportFiles: 'cucumber.json',
                    reportName: 'Cucumber Test Report',
                    allowMissing: true,
                    alwaysLinkToLastBuild: true,
                    keepAll: true
                ])
            }
        }

        stage('Build & Deploy App') {
            steps {
                echo '🚀 Deploying Spring Boot app with MySQL using Docker Compose...'
                bat "docker-compose -f ${DOCKER_COMPOSE_FILE} down"
                bat "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d --build"
                bat 'powershell -Command "Start-Sleep -Seconds 20"' // wait for services
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
