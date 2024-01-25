pipeline {
    agent any

    tools {
        jdk 'A408_BE_Build'
    }
    environment {
        CONTAINER_NAME = "auto-dev-server"
        SSH_CREDENTIALS = 'DevOps'
        REMOTE_HOST = 'ssafyhelper.shop'
        SCRIPT_PATH = '/temp/AutoDevServer.sh'
        SSH_REMOTE_CONFIG = 'ubuntu'
    }

    stages {

        stage('Build BE') {
            steps {
                script {
                    dir('BE') {
                        sh 'chmod +x gradlew'
                        sh 'ls -l'

                        sh './gradlew clean build'
                        sh 'jq --version'
                        sh 'cd build/libs && ls -al'

                        sshPublisher(
                            publishers: [
                                sshPublisherDesc(
                                    configName: 'ssafyhelper',
                                    transfers: [
                                        sshTransfer(
                                            sourceFiles: 'build/libs/goumunity-0.0.1-SNAPSHOT.jar',
                                            removePrefix: '/build/libs',
                                            remoteDirectory: '/sendData'
                                        )
                                    ]
                                )
                            ]
                        )

                        def remote = [:]
                        remote.name = 'DevOps'  // Credential ID
                        remote.host = 'ssafyhelper.shop'
                        remote.user = 'ubuntu'
                        remote.identityFile = credentials('DevOps')  // Credential ID

                        sshCommand(remote: remote, command: 'ls -l')
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Build successful!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}
