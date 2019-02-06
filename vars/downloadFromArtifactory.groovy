def call(Map pipelineParams) {
    Map requiredKeys = [artifactoryUrl: '', artifactoryCredentialsId: '', artifactoryRepo: '', buildName: '', buildNumber: '']

    requiredKeys.keySet().each { key ->
        if(!pipelineParams.containsKey(key))
            error("Missing parameter key: ${key}")
    }

    withCredentials() {
        def spec = """{
            "files": [
                {
                    "repo": "${pipelineParams.artifactoryRepo}",
                    "artifact.module.build.name": "${pipelineParams.buildName}",
                    "artifact.item.module.build.number": "${pipelineParams.buildNumber}"
                }
            ]
        }
        """
        def server = Artifactory.newServer url: pipelineParams.artifactoryUrl, credentialsId: pipelineParams.artifactoryCredentialsId

        server.download spec: spec
    }
}