#  Jenkins Shared Libraries

## **Objective**

### Implement shared libraries in Jenkins to reuse code across multiple pipelines.: Create a shared library for common tasks and demonstrate its usage in different pipelines.

## **Steps:**

### **Prerequistes**
Before running this lab, ensure you have the following:
- add **Docker Hub** credentials (username,password) in Jenkins credentials 
Ensure you have the following files:
- Jenkins > src/vars/`dockerBuild.groovy`, `dockerPush.groovy`, `updateYaml.groovy`, `deployK8s.groovy`
- `Dockerfile`
- `index.html`
- `deployment.yaml`
     
#### 1. Configure Jenkins to Use the Shared Library

Jenkins > Manage Jenkins > Configure System > Global Pipeline Libraries > (use the same repository and specify the path to the shared library directory.)
- Name: my-shared-library
- Source Code Management: Git
- Repository URL: https://github.com/your-username/your-repo.git
- Branch: main
- Library Directory: src/vars (this tells Jenkins to look for the library in the src/vars directory within the repository)
![image](https://github.com/user-attachments/assets/78434cc7-b237-41ea-a6d9-65f0a9a3f4f0)

#### 2. Use the Shared Library in Your Pipeline: Now that the library is configured, can use it in Jenkinsfile as before:
```
@Library('my-shared-library') _

pipeline {
    agent any
    environment {
        DOCKER_HUB_CREDENTIALS = credentials('docker-hub-credentials')
        IMAGE_NAME = "nginx-test"
        IMAGE_TAG = "latest"
        REGISTRY = "docker.io"
        DEPLOYMENT_FILE = "deployment.yaml"
        K8S_TOKEN = credentials('k8s-token') // Token for Kubernetes authentication
    }

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/mabuelmagdd/test.git'
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

        stage('Update Deployment YAML') {
            steps {
                script {
                    updateYaml(DEPLOYMENT_FILE, REGISTRY, DOCKER_HUB_CREDENTIALS_USR, IMAGE_NAME, IMAGE_TAG)
                }
            }
        }

        stage('Deploy to Minikube') {
            steps {
                script {
                    deployK8s(DEPLOYMENT_FILE)
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

```
 #### 3. Build the pipeline
 ![image](https://github.com/user-attachments/assets/fdd37bae-bc96-4053-9c3c-32ba90e9303b)

 #### 4. Check Deployments and Pods in the cluster  
![image](https://github.com/user-attachments/assets/e21d761b-5ee9-47fb-8adf-6390c7c3502a)


