# Jenkins Pipeline Setup Guide

## Prerequisites

Before running the Jenkins pipeline, ensure the following components and configurations are set up correctly:

### 1. Jenkins Plugins
To ensure the pipeline runs smoothly, install the following Jenkins plugins:

- **Pipeline**: To define and manage Jenkins pipelines.
- **Git**: To clone the repository.
- **Docker Pipeline**: To interact with Docker images for building and pushing images.
- **Kubernetes CLI Plugin**: To interact with Kubernetes (Minikube in this case).
- **SonarQube Scanner for Jenkins**: To run SonarQube analysis during the pipeline.
- **Credentials Binding Plugin**: To securely handle credentials such as Docker credentials, SonarQube tokens, and Kubernetes credentials.

### 2. SonarQube Setup
Before running the pipeline, configure Jenkins to connect to SonarQube:

#### Login to SonarQube
- Access your SonarQube instance and log in.
- Generate a new token by going to **My Account** → **Security** → **Generate Tokens**.
- Copy the generated token for later use.

#### Set up SonarQube URL in Jenkins
1. Navigate to **Jenkins Dashboard** → **Manage Jenkins** → **Configure System**.
2. Scroll down to the **SonarQube Servers** section.
3. Add a new SonarQube server by entering:
   - **Server Name**: SonarQube (or any preferred name).
   - **Server URL**: `http://<your-sonarqube-url>`
   - **Authentication Token**: Paste the token you copied earlier.

### 3. Jenkins Shared Library
To use shared libraries in Jenkins:

1. Ensure the shared library (`my-shared-library`) is stored in a Git repository accessible by Jenkins.
2. In **Jenkins**, go to **Manage Jenkins** → **Configure System** → **Global Pipeline Libraries**.
3. Add the shared library by specifying:
   - **Name**: `my-shared-library`
   - **Repository URL**: The Git repository URL of your shared library.
   - **Branch**: `main` (or any branch you prefer).
   - **Credentials**: If the repository is private, provide the necessary credentials.

### 4. Jenkins Credentials Setup
You will need to create and configure credentials for Docker, Minikube, and SonarQube.

#### Docker Credentials:
1. Go to **Jenkins Dashboard** → **Manage Jenkins** → **Manage Credentials** → **(global)** → **Add Credentials**.
2. Set **Kind** to **Username with password**.
3. Enter your **Docker Hub username** and **password**.
4. Save the credentials with the ID `docker-hub-credentials`.

#### Minikube Credentials:
1. Store the Kubernetes credentials, including `kubeconfig`, `cacertificates`, and `k8s-token`.
2. Add these credentials in **Jenkins** under **Manage Jenkins** → **Manage Credentials** → **(global)**.
   - **K8S Token**: Store the Kubernetes token as `k8s-token`.

#### SonarQube Credentials:
1. In Jenkins, go to **Manage Jenkins** → **Manage Credentials** → **(global)** → **Add Credentials**.
2. Set **Kind** to **Secret text**.
3. Enter the SonarQube authentication token and give it the ID `sonar-token`.

### 5. Minikube Configuration
Ensure that Jenkins has access to Minikube's critical files:

1. **Kubeconfig File**: The `kubeconfig` file is essential for Jenkins to interact with the Minikube cluster. Make sure this file is stored securely in Jenkins' credentials store or within the pipeline environment.
   
2. **CA Certificates**: Ensure the CA certificates required by Minikube are available for the pipeline to verify the Kubernetes API server's identity.

### Running the Pipeline

Once the setup is complete, you can execute the pipeline. Here’s a breakdown of the pipeline stages:

#### 1. **Clone Repository**
   - Checks out the code from your Git repository.

#### 2. **Unit Test**
   - Runs unit tests on the code.

#### 3. **Build JAR**
   - Builds the Java application JAR file.

#### 4. **SonarQube Analysis**
   - Runs SonarQube analysis using the configured SonarQube server.

#### 5. **Build Docker Image**
   - Builds a Docker image using the `Dockerfile`.

#### 6. **Push Docker Image to Docker Hub**
   - Pushes the built Docker image to Docker Hub.

#### 7. **Deploy to Minikube**
   - Deploys the application to Minikube using Kubernetes YAML files (`deployment.yaml`, `service.yaml`).

#### 8. **Post Actions**
   - Cleans up by removing the Docker image after the deployment.

---

### Notes:
- Ensure that all the credentials and configuration details are correctly set up in Jenkins to avoid authentication errors.
- The shared library functions used in the pipeline, such as `gitCheckout()`, `unitTest()`, `dockerBuild()`, and `deployK8s()`, should be defined in your shared library and be available for the pipeline to use.
