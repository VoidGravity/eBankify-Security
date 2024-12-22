pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/VoidGravity/eBankify-Security.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.host.url=http://localhost:9000'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ebankify-app .'
            }
        }

        stage('Deploy') {
            steps {
                sh 'docker run -d -p 8085:8080 ebankify-app'
            }
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully!'
        }
    }
}