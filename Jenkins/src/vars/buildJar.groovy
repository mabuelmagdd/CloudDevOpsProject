def call() {
    echo "Building JAR file..."

    try {
        // Run the Gradle build task to generate the JAR file
        sh './gradlew bootJar'
    } catch (Exception e) {
        currentBuild.result = 'FAILURE'
        throw e
    }
}
