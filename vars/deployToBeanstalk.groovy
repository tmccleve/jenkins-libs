def call(Map pipelineParams) {
    Map requiredKeys = [awsRegion: '', artifactoryUrl: '', artifactoryRepo: '', artifactoryCredentialsId: '', buildName: '',
                        buildNumber: '', applicationName: '', envName: '', bucketName: '', s3KeyPrefix: '',
                        rootObject: '', versionDesc: '', versionLabel: '']

    requiredKeys.keySet().each { key ->
        if(!pipelineParams.containsKey(key))
            error("Missing parameter key: ${key}")
    }

    pipeline {
        stages {
            stage('fetch artifact') {
                steps {
                    downloadFromArtifactory(artifactoryUrl: pipelineParams.artifactoryUrl,
                            artifactoryCredentialsId: pipelineParams.artifactoryCredentialsId,
                            artifactoryRepo: pipelineParams.artifactoryRepo,
                            buildName: pipelineParams.buildName,
                            buildNumber: pipelineParams.buildNumber
                    )
                }
            }
            stage('deploy beanstalk') {
                steps {
                    step([$class: 'AWSEBDeploymentBuilder',
                            applicationName: pipelineParams.applicationName,
                            awsRegion: pipelineParams.awsRegion,
                            bucketName: pipelineParams.bucketName,
                            checkHealth: true,
                            environmentName: pipelineParams.envName,
                            keyPrefix: pipelineParams.s3KeyPrefix,
                            maxAttempts: 30,
                            rootObject: pipelineParams.rootObject,
                            sleepTime: 90,
                            versionDescriptionFormat: pipelineParams.versionDesc,
                            versionLabelFormat: pipelineParams.versionLabel,
                            zeroDowntime: false
                    ])
                }
            }
        }
    }
}
