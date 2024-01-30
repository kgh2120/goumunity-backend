pipeline {
    agent any

    tools {
        jdk 'A408_BE_Build'
    }
    environment {
        CONTAINER_NAME = "auto-dev-server"
        SSH_CREDENTIALS = 'DevOps'
        REMOTE_HOST = '172.31.41.136'
        SCRIPT_PATH = '/temp/AutoDevServer.sh'
        SSH_REMOTE_CONFIG = 'ubuntu'
    }

    stages {

        stage('Build BE && ') {
            steps {
                script {

                    dir('BE') {
                        sh 'chmod +x gradlew'
                        sh 'ls -l'

                        sh './gradlew clean build spotlessApply'
                        sh 'jq --version'
                        sh 'cd build/libs && ls -al'
                        
                        
                    }
                }
            }
        }
        stage('Send Artifact'){
            steps{
                script{
                    sh 'ls -al'
                    sh 'ls -al BE/build'
                    sh 'cd BE/build/libs && ls -al'
                    sshPublisher(
                                publishers: [
                                    sshPublisherDesc(
                                        configName: 'ssafycontrol',
                                        transfers: [
                                            sshTransfer(
                                                sourceFiles: 'BE/build/libs/goumunity-0.0.1-SNAPSHOT.jar',
                                                removePrefix: 'BE/build/libs',
                                                remoteDirectory: '/sendData',
                                                // execCommand: 'sh temp/AutoDevServer.sh'
                                            )
                                        ]
                                    )
                                ]
                            )
                }
            }
        }
        
        stage('Auto CI By Git-lab CI-CD'){
            steps{
                script{
                    sh 'echo manual Auto CI Start'
                    sh 'curl "https://ssafycontrol.shop/control/dev/be"'
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
