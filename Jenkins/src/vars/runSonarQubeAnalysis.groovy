def call(String sonarServer) {
    // Run the SonarQube analysis
    withSonarQubeEnv(sonarServer) {
        sh """
        ./gradlew sonarqube 
        """
    }
}
