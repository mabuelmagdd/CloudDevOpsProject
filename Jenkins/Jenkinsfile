@Library('my-shared-library@main') _  // Load the shared library

pipeline {
    agent any
    environment {
        DOCKER_HUB_CREDENTIALS = credentials('docker-hub-credentials')
        IMAGE_NAME = "finalprojectcode"
        IMAGE_TAG = "latest"
        REGISTRY = "docker.io"
        DEPLOYMENT_FILE = "deployment.yaml"
        SERVICE_FILE = "service.yaml"
        K8S_TOKEN = credentials('k8s-token') // Token for Kubernetes authentication
        SONARQUBE_SERVER = 'SonarQube'
    }

    stages {
        stage('Clone Repository') {
            steps {
               echo "Testing shared library!"
               gitCheckout()
            }
        }
        
        stage('Unit Test') {
                steps {
                    unitTest()
                }
            }
        
        stage('Build JAR') {
            steps {
                buildJar()
            }
        }
        
        stage('SonarQube') {
            steps {
                runSonarQubeAnalysis()
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    dockerBuild('Dockerfile', IMAGE_NAME, IMAGE_TAG, REGISTRY, DOCKER_HUB_CREDENTIALS_USR, DOCKER_HUB_CREDENTIALS_PSW)
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    dockerPush(IMAGE_NAME, IMAGE_TAG, REGISTRY, DOCKER_HUB_CREDENTIALS_USR, DOCKER_HUB_CREDENTIALS_PSW)
                }
            }
        }
        
        stage('Deploy to Minikube') {
            steps {
                script {
                    deployK8s(DEPLOYMENT_FILE,SERVICE_FILE)
                }
            }
        }
    }

    post {
        always {
            script {
                sh 'docker rmi ${REGISTRY}/${DOCKER_HUB_CREDENTIALS_USR}/${IMAGE_NAME}:${IMAGE_TAG}'
            }
        }
    }
}
