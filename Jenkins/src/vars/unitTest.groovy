def call() {
    // This function will run unit tests using Gradle in a Jenkins pipeline.
    echo "Running Unit Tests..."
        // Run unit tests using Gradle wrapper
       sh '''
        chmod +x ./gradlew
        ./gradlew test
        '''
}
