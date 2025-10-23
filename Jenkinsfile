pipeline {
    agent any

    environment {
        REPO_URL = 'https://github.com/AyeKyiPyar/player-team-cucumber.git'
        DOCKER_COMPOSE_FILE = 'docker-compose.yml'
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

     

       stage('Deploy') {
            steps {
                echo '🚀 Deploying Spring Boot app with MySQL using Docker Compose...'
                // Stop old containers (ignore errors)
                bat 'docker-compose -f %DOCKER_COMPOSE_FILE% down || exit 0'
                // Build images and start containers
                bat 'docker-compose -f %DOCKER_COMPOSE_FILE% up -d --build'

                // Wait for MySQL to be ready
                echo '⏳ Waiting for MySQL to be fully initialized...'
                bat '''
                powershell -Command "
                $maxRetries = 15;
                $retry = 0;
                do {
                    try {
                        docker exec mysql_db mysqladmin ping -uroot -proot -h localhost > $null 2>&1
                        $status = $LASTEXITCODE
                    } catch { $status = 1 }
                    if ($status -ne 0) { Start-Sleep -Seconds 5; $retry++ }
                } while ($status -ne 0 -and $retry -lt $maxRetries);
                if ($status -ne 0) { exit 1 }
                "
                '''
            }
        }
    stage('Run Cucumber Tests') {
            steps {
                echo 'Running Cucumber tests...'
                // Run tests inside the Jenkins workspace
                bat 'mvn test -Dcucumber.options="--plugin json:target/cucumber.json"'
            }
        }

        stage('Publish Cucumber Results') {
            steps {
                cucumber buildStatus: 'UNSTABLE', jsonReportDirectory: 'target', fileIncludePattern: 'cucumber.json'
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
