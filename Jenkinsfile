pipeline {
    agent any

    tools{
	jdk 'A408_BE_Build'
    }
    environment {
        CONTAINER_NAME = "auto-dev-server"
        SSH_CREDENTIALS = 'ssafyhelperpem'
        REMOTE_HOST = 'ssafyhelper.shop'
        SCRIPT_PATH = '/temp/AutoDevServer.sh'
        SSH_REMOTE_CONFIG = 'ssafyhelper'
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
                        
                        // sshPublisher(
                        //     publishers: [
                        //         sshPublisherDesc(
                        //             configName: 'ssafyhelper', 
                        //             transfers: [
                        //                 sshTransfer(
                        //                     execCommand: 'sudo chmod +x AutoDevServer.sh && sudo sh AutoDevServer.sh > output.log 2>&1' 
                        //                 )
                        //             ]
                        //         )
                        //     ]
                        // )
                        
                        sshCommand remote: [
                        host: REMOTE_HOST,
                        credentialsId: SSH_CREDENTIALS,
                        user: 'ubuntu', // Update with your SSH username
                        allowAnyHosts: true
                    ], script: SCRIPT_PATH

                        sh 'echo manual Auto CI Start'
                        sh 'curl "https://www.ssafyhelper.shop/control/dev/be"'

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

