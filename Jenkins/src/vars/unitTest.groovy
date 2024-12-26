def call() {
    // This function will run unit tests using Gradle in a Jenkins pipeline.
    echo "Running Unit Tests..."

    try {
        // Run unit tests using Gradle wrapper
        sh 'chmod +x gradlew'
        sh './gradlew test'
    } catch (Exception e) {
        currentBuild.result = 'FAILURE'
        throw e
    }
}
