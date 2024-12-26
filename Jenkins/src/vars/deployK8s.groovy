def call(String DEPLOYMENT_FILE, String SERVICE_FILE) {
    sh '''
    export KUBECONFIG=~/.kube/config
    # Check if the file exists
            ls -l ${DEPLOYMENT_FILE}
            ls -l ${SERVICE_FILE}
            
    echo "Applying Kubernetes deployment file: ${DEPLOYMENT_FILE}"
    kubectl apply -f ${DEPLOYMENT_FILE}
    echo "Applying Kubernetes deployment file: ${SERVICE_FILE}"
    kubectl apply -f ${SERVICE_FILE}
    '''
}

