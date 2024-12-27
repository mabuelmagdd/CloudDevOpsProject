def call(String sonarProjectKey, String sonarHostUrl, String sonarLogin) {
    // Run the SonarQube analysis
    withSonarQubeEnv('SonarQube') {
        sh """
        ./gradlew sonarqube -Dsonar.projectKey=${sonarProjectKey} \
                              -Dsonar.host.url=${sonarHostUrl} \
                              -Dsonar.login=${sonarLogin}
        """
    }
}
