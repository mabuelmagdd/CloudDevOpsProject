def call() {
    // Checkout the repository from GitHub with the 'main' branch
    echo "Cloning repository from https://github.com/mabuelmagdd/FinalProjectCode"
    checkout([
        $class: 'GitSCM',
        branches: [[name: '*/main']],  // Specify the branch
        doGenerateSubmoduleConfigurations: false,
        extensions: [],
        userRemoteConfigs: [[url: 'https://github.com/mabuelmagdd/FinalProjectCode']]
    ])
}
