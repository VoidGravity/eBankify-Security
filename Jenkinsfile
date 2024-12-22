pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'M3'
    }

    environment {
        SONAR_TOKEN = credentials('sonarqube-token')
    }

//     stages {
//         stage('Verify Tools') {
//             steps {
//                 bat 'java -version'
//                 bat 'mvn -version'
//             }
//         }
//
//         stage('Checkout') {
//             steps {
//                 git branch: 'main', url: 'https://github.com/VoidGravity/eBankify-Security.git'
//             }
//         }
//
//         stage('Build') {
//             steps {
//                 bat 'mvn clean package -DskipTests'
//             }
//         }
//
//         stage('Test') {
//             steps {
//                 bat 'mvn test'
//             }
//         }
//
//         stage('SonarQube') {
//             steps {
//                 bat """
//                     mvn sonar:sonar \
//                     -Dsonar.host.url=http://localhost:9000 \
//                     -Dsonar.login=${SONAR_TOKEN}
//                 """
//             }
//         }
//
//         stage('Build Docker Image') {
//             steps {
//                 bat 'docker build -t ebankify-app .'
//             }
//         }
//
//         stage('Deploy') {
//             steps {
//                 bat 'docker run -d -p 8085:8080 ebankify-app'
//             }
//         }
//     }
    stages {
            stage('Checkout') {
                steps {
                    git branch: 'main', url: 'YOUR_GITHUB_REPO_URL'
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

            stage('SonarQube Analysis') {
                steps {
                    withSonarQubeEnv('SonarQube') {
                        sh 'mvn sonar:sonar'
                    }
                }
            }

            stage('Build Docker Image') {
                steps {
                    script {
                        sh 'docker build -t ebankify-app .'
                    }
                }
            }

            stage('Deploy') {
                steps {
                    script {
                        sh '''
                            docker network create ebankify-network || true
                            docker network connect ebankify-network gdc || true
                            docker run -d --name ebankify-app --network ebankify-network -p 8085:8081 ebankify-app
                        '''
                    }
                }
            }
        }
    post {
        always {
            echo 'Pipeline finished'
        }
    }
}