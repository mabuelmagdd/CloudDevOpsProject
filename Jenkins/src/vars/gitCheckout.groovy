def call() {
    git branch: 'main', url = 'https://github.com/Ibrahim-Adell/FinalProjectCode'
    echo "Cloning repository from ${repoUrl}"
    checkout scm
}
