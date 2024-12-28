# Ansible Configuration and Automation

This README provides an overview of the configuration and automation setup using Ansible for various components in the Cloud DevOps Project. It explains the purpose of each component, emphasizes the use of Ansible roles and dynamic inventory, and briefly lists the tasks managed by Ansible playbooks.

### Ansible Roles and Dynamic Inventory:
- **Roles**: The Docker configuration is managed through a dedicated **Ansible role**. This ensures better modularity and reusability, allowing the role to be used across multiple environments if necessary.
- **Dynamic Inventory**: Ansible dynamically fetches the list of target hosts (EC2 instances) from the infrastructure provisioned by Terraform. This removes the need to manually manage static IP addresses in the playbooks.

## Table of Contents
1. [Docker](#docker)
2. [Minikube](#minikube)
3. [Jenkins](#jenkins)
4. [SonarQube](#sonarqube)

---

## Docker

### What is Docker?
Docker is a platform used for developing, shipping, and running applications inside lightweight, portable containers. It allows developers to package applications with all their dependencies into a container that can run consistently across different environments.

### What Does It Do in This Project?
In this project, Docker is used to containerize the application, ensuring it runs seamlessly in any environment without dependency issues. Ansible is used to automate the installation and configuration of Docker on the EC2 instance.

### Ansible Tasks for Docker:
The Docker role automates the installation and setup of Docker on the EC2 instance. Below is a brief description of the tasks executed in this role:

1. **Update Package Index**:
   - Updates the package cache on the EC2 instance to ensure the system is aware of the latest available package versions.

2. **Add Docker GPG Key**:
   - Adds the official Docker GPG key to verify the authenticity of the Docker packages before installation.

3. **Add Docker Repository**:
   - Adds the official Docker APT repository to enable the installation of Docker from the Docker repository.

4. **Install Docker**:
   - Installs Docker using the `docker-ce` package, ensuring Docker is available on the instance.

5. **Ensure Docker Service is Running**:
   - Ensures that the Docker service is started and enabled to automatically start on system boot.

---

## Minikube

### What is Minikube?
Minikube is a tool for running Kubernetes clusters locally. It allows developers to simulate a Kubernetes environment on their local machine, making it easier to test applications in a Kubernetes environment before deploying to a cloud-based cluster.

### What Does It Do in This Project?
Minikube is used for creating a local Kubernetes environment to simulate deployment and orchestration of Docker containers. This allows for testing before pushing to a production Kubernetes cluster.

### Ansible Tasks for Minikube:
1. **Install kubectl using Snap**:
   - Installs `kubectl`, the Kubernetes command-line tool, using Snap. This tool is essential for interacting with the Minikube cluster.

2. **Download Minikube Binary**:
   - Downloads the latest version of the Minikube binary to the target system.

3. **Move Minikube Binary to /usr/local/bin**:
   - Moves the Minikube binary to a location in the system’s PATH (`/usr/local/bin`), ensuring that it can be executed from anywhere.

4. **Add User to Docker Group**:
   - Adds the `ubuntu` user to the `docker` group to grant permissions for Docker-related actions, allowing Minikube to use Docker as its driver.

5. **Log Out and Back In to Apply Docker Group Changes**:
   - Triggers a logout to apply the group changes made in the previous step. This step is necessary for the user to gain the correct Docker permissions.

6. **Start Minikube as Non-Root User**:
   - Starts Minikube with specific resource limits (memory and CPU), using Docker as the driver. This task ensures that Minikube is running in a non-root context.

7. **Wait for Minikube Profile to be Created**:
   - Pauses the playbook for 3 minutes to allow Minikube time to initialize and set up the necessary configuration.

8. **Check Minikube Status**:
   - Checks the status of Minikube to ensure that it is running. The task retries up to 5 times with a 10-second delay between retries, ensuring that Minikube starts successfully.

9. **Debug Minikube Status**:
   - Outputs the status of Minikube for debugging purposes. This task helps to verify that Minikube has started correctly and provides insight into any issues.

10. **Create Minikube Tunnel Service File**:
    - Creates a `systemd` service for Minikube Tunnel, which enables Kubernetes services to be accessible on the local machine. The tunnel allows you to access services from the Kubernetes cluster on your host machine.

11. **Reload Systemd Daemon**:
    - Reloads the `systemd` manager to recognize the newly created Minikube tunnel service.

12. **Enable Minikube Tunnel Service**:
    - Enables the Minikube tunnel service to start on boot and starts it immediately. This service ensures that the Minikube network tunnel remains active for accessing Kubernetes services.

---

## Jenkins

### What is Jenkins?
Jenkins is an open-source automation server used for continuous integration and continuous delivery (CI/CD). It helps automate the process of building, testing, and deploying applications.

### What Does It Do in This Project?
In this project, Jenkins is used to automate the CI/CD pipeline, triggering builds, running tests, and deploying to different environments (e.g., Kubernetes, OpenShift).

### Ansible Tasks for Jenkins:
The following is a brief description of the tasks managed by the **Jenkins role** in the project:

1. **Update Package Index**:
   - Updates the package cache on the EC2 instance to ensure the system is aware of the latest available package versions.

2. **Install OpenJDK 17**:
   - Installs OpenJDK 17, which is required for Jenkins to run. Jenkins needs Java to function, and OpenJDK 17 is installed to meet this requirement.

3. **Verify Java Installation**:
   - Verifies that Java has been installed correctly by running the `java -version` command. This step helps to debug any issues with the Java installation.

4. **Add Jenkins Repository Key**:
   - Downloads and adds the official Jenkins GPG key to the system to verify the authenticity of the Jenkins packages.

5. **Add Jenkins Repository**:
   - Adds the Jenkins APT repository to the list of sources on the EC2 instance, enabling the installation of Jenkins from the official Jenkins package repository.

6. **Update Package Index Again**:
   - Updates the package cache again to include the newly added Jenkins repository.

7. **Install Jenkins**:
   - Installs the Jenkins package from the official Jenkins repository, ensuring Jenkins is available on the EC2 instance.

8. **Start Jenkins Service**:
   - Ensures that the Jenkins service is started after installation. This task checks that Jenkins is running and ready to be accessed.

9. **Enable Jenkins to Start on Boot**:
   - Configures Jenkins to start automatically on system boot, ensuring it is always running when the EC2 instance is restarted.

10. **Allow Port 8080 Through UFW**:
    - Configures the firewall to allow traffic on port 8080, the default port for accessing Jenkins via a web browser.

---

## SonarQube

### What is SonarQube?
SonarQube is an open-source platform for continuous inspection of code quality. It provides detailed reports on code quality issues, such as bugs, vulnerabilities, and code smells, helping developers maintain clean and reliable code.

### What Does It Do in This Project?
In this project, SonarQube is integrated into the CI/CD pipeline to analyze the code for quality issues before deploying it to production environments.

### Ansible Tasks for SonarQube:
1. **Update apt cache**  
   - Updates the package cache to ensure all apt packages are up-to-date.

2. **Install prerequisites**  
   - Installs Java, wget, unzip, gnupg, and ca-certificates which are required to run SonarQube.

3. **Install required packages**  
   - Installs additional packages like Python dependencies, nginx, and acl needed for SonarQube setup.

4. **Update APT repositories and cache on Debian/Ubuntu**  
   - Forces an update of apt repositories to get the latest package information.

5. **Upgrade all packages**  
   - Upgrades all installed packages to the latest versions to ensure the system is up-to-date and secure.

6. **Set up Postgres 14 repo**  
   - Adds the PostgreSQL repository to the system for installing PostgreSQL 14, which is used by SonarQube.

7. **Install PostgreSQL**  
   - Installs PostgreSQL 14 and ensures the service is started.

8. **Ensure PostgreSQL is listening on all addresses**  
   - Configures PostgreSQL to listen on all IP addresses for database access by SonarQube.

9. **Add new configuration to "pg_hba.conf"**  
   - Updates PostgreSQL’s configuration to allow connections from any IP address.

10. **Create a Superuser PostgreSQL database user**  
    - Creates a superuser in PostgreSQL for administrative tasks.

11. **Change password for PostgreSQL user**  
    - Updates the OS-level password for the PostgreSQL user to sync with PostgreSQL.

12. **Create Sonar User in PostgreSQL**  
    - Creates a user in PostgreSQL with the necessary permissions for SonarQube to access the database.

13. **Create SonarQube Database**  
    - Creates a database for SonarQube to store its data.

14. **Ensure vm.max_map_count is set correctly**  
    - Ensures the `vm.max_map_count` kernel parameter is set to a value required by SonarQube.

15. **Persist vm.max_map_count in sysctl.conf**  
    - Makes the `vm.max_map_count` setting persistent across system reboots.

16. **Download SonarQube**  
    - Downloads the SonarQube zip file to be extracted and installed.

17. **Extract SonarQube**  
    - Extracts the SonarQube zip file to the `/opt` directory.

18. **Ensure group "sonar" exists**  
    - Creates the "sonar" group for SonarQube user permissions.

19. **Add the user 'sonar'**  
    - Creates a user with specific permissions and home directory for SonarQube.

20. **Recursively change ownership of the SonarQube directory**  
    - Changes the ownership of SonarQube directory to the "sonar" user and group.

21. **SonarQube configuration**  
    - Configures SonarQube properties including database credentials and web settings.

22. **Create SonarQube service file**  
    - Creates a systemd service file for SonarQube to run as a service.

23. **Reload systemd**  
    - Reloads systemd to apply the new SonarQube service configuration.

24. **Start SonarQube**  
    - Starts the SonarQube service.

25. **Enable SonarQube service to start on boot**  
    - Enables SonarQube to automatically start on system boot.

26. **Allow port 9000 through UFW**  
    - Opens port 9000 in the firewall to allow access to SonarQube’s web interface.
---

## Conclusion

This Ansible automation setup ensures that each component of the Cloud DevOps project is configured and deployed correctly. By using Ansible roles for modularity and dynamic inventory for flexible infrastructure management, the system is streamlined for efficiency. This approach allows for better scalability and maintainability while ensuring consistent configuration across environments.

