def call(def credentialId, def cmd) {
  withCredentials([usernamePassword(credentialsId: credentialId,
    usernameVariable: 'AWS_ACCESS_KEY_ID',
    passwordVariable: 'AWS_SECRET_ACCESS_KEY')]) {
       sh "aws ${cmd}"
    }
}
