pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'M3'
    }

    environment {
        SONAR_TOKEN = credentials('sonarqube-token')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/VoidGravity/eBankify-Security.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                bat """
                    mvn sonar:sonar \
                    -Dsonar.projectKey=ebankify \
                    -Dsonar.projectName=ebankify \
                    -Dsonar.host.url=http://localhost:9000 \
                    -Dsonar.token=%SONAR_TOKEN%
                """
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t ebankify-app .'
            }
        }

        stage('Deploy') {
            steps {
                bat '''
                    docker network create ebankify-network || exit 0
                    docker network connect ebankify-network gdc || exit 0
                    docker stop ebankify-app || exit 0
                    docker rm ebankify-app || exit 0
                    docker run -d --name ebankify-app --network ebankify-network -p 8085:8081 ebankify-app
                '''
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished'
            junit '**/target/surefire-reports/*.xml'
        }
    }
}