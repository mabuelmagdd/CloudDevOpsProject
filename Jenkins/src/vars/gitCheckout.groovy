def call() {
    git branch: 'main', url = 'https://github.com/mabuelmagdd/FinalProjectCode'
    echo "Cloning repository from ${repoUrl}"
    checkout scm
}
