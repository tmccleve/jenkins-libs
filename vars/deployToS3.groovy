def call(Map pipelineParams) {
    Map requiredKeys = [awsCredentialsId: '', branch: '', bucketName: '', gitCredentialsId: '', gitUrl: '', s3KeyPrefix: '']

    requiredKeys.keySet().each { key ->
        if(!pipelineParams.containsKey(key))
            error("Missing parameter key: ${key}")
    }

    pipeline {
        stages {
            stage('checkout git') {
                steps {
                    git branch: pipelineParams.branch, credentialsId: pipelineParams.gitCredentialsId, url: pipelineParams.gitUrl
                }
            }

            stage('deploy') {
                steps {
                   awsCmd(pipelineParams.awsCredentialsId, "s3 sync . s3://${pipelineParams.bucketName}/${pipelineParams.s3KeyPrefix}")
                }
            }
        }
    }
}