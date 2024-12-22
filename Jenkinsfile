pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'M3'
    }

    stages {
        stage('Verify Tools') {
            steps {
                bat 'java -version'
                bat 'mvn -version'
            }
        }

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

        stage('SonarQube') {
            steps {
                bat 'mvn sonar:sonar -Dsonar.host.url=http://localhost:9000'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t ebankify-app .'
            }
        }

        stage('Deploy') {
            steps {
                bat 'docker run -d -p 8085:8080 ebankify-app'
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished'
        }
    }
}