def call(def credentialId, def cmd) {
    withCredentials([$class: 'AmazonWebServicesCredentialsBinding',
                       accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                       credentialsId: credentialId,
                       ecretKeyVariable: 'AWS_SECRET_ACCESS_KEY']) {
       sh "aws ${cmd}"
    }
}