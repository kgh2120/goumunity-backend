pipeline {
    agent any

    tools{
	jdk 'A408_BE_Build'
    }
    environment {
        CONTAINER_NAME = "auto-dev-server"
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
                        host: 'ssafyhelper.shop',
                        credentialsId: 'ssafyhelperpem',
                        user: 'ubuntu'
                    ], command: 'bash -s', script: """
                        export PATH=$PATH:/usr/local/bin

                        # 실행하려는 디렉토리로 이동합니다.
                        cd /home/ubuntu/temp/sendData

                        # 디렉토리 내 파일 목록 출력
                        echo \$(ls -l)

                        # Docker 이미지 빌드
                        sudo docker build -t \$CONTAINER_NAME .

                        # 실행 중인 컨테이너 확인 후 중단
                        if sudo docker ps -q --filter "name=\$CONTAINER_NAME" >/dev/null; then
                            echo "Stopping container: \$CONTAINER_NAME"
                            sudo docker stop "\$CONTAINER_NAME"
                        else
                            echo "Container \$CONTAINER_NAME is not running."
                        fi

                        # Docker 컨테이너 실행
                        sudo docker run -d -p 8081:8081 --rm -v /var/logs/dev-server:/logs --name \$CONTAINER_NAME \$CONTAINER_NAME
                    """

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

