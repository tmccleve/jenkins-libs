def call(def credentialId, def cmd) {
  withAWS(credentials: credentialId) {
       sh "aws ${cmd}"
    }
}
