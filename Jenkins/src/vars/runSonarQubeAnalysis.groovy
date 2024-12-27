def call() {
    // Get the EC2 Instance IP by querying AWS CLI or using any method that returns the IP address
    def ec2Ip = sh(script: 'curl http://169.254.169.254/latest/meta-data/public-ipv4', returnStdout: true).trim()
    
    // Construct the SonarQube URL using the EC2 public IP
    def sonarQubeUrl = "http://${ec2Ip}:9000"
    
    // Store the SonarQube URL in the environment variable
    env.SONARQUBE_URL = sonarQubeUrl

    // Display the EC2 public IP address and SonarQube URL
    echo "The EC2 public IP address is: ${ec2Ip}"
    echo "SonarQube URL is: ${env.SONARQUBE_URL}"

    // Run the SonarQube analysis
    withSonarQubeEnv('SonarQube') {
        sh """
        ./gradlew sonarqube -Dsonar.projectKey=cloudDevOps-project \
                              -Dsonar.host.url=${SONARQUBE_URL} \
                              -Dsonar.login=${SONARQUBE_TOKEN}
        """
    }
}
