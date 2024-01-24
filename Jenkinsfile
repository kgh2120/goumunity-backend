pipeline {
    agent any

    tools{
	jdk 'A408_BE_Build'
    }

    environment{
        JSON_DATA=''
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
echo $(ls -l)

# Docker 이미지 빌드
sudo docker build -t auto-dev-server .

# 컨테이너 이름
container_name="auto-dev-server"

# 실행 중인 컨테이너 확인 후 중단
if sudo docker ps -q --filter "name=${container_name}" >/dev/null; then
    echo "Stopping container: ${container_name}"
    sudo docker stop "${container_name}"
else
    echo "Container ${container_name} is not running."
fi

# Docker 컨테이너 실행
sudo docker run -d -p 8081:8081 --rm -v /var/logs/dev-server:/logs --name auto-dev-server auto-dev-server
cat AutoDevServer.sh
#!/bin/bash

# Jenkins 환경에서 실행되는지 확인하기 위해 아래 환경 변수를 설정합니다.
export PATH=$PATH:/usr/local/bin

# 실행하려는 디렉토리로 이동합니다.
cd /home/ubuntu/temp/sendData


# Docker 이미지 빌드
sudo docker build -t auto-dev-server .

# 컨테이너 이름
container_name="auto-dev-server"

# 실행 중인 컨테이너 확인 후 중단
if sudo docker ps -q --filter "name=${container_name}" >/dev/null; then
    echo "Stopping container: ${container_name}"
    sudo docker stop "${container_name}"
else
    echo "Container ${container_name} is not running."
fi

# Docker 컨테이너 실행
sudo docker run -d -p 8081:8081 --rm -v /var/logs/dev-server:/logs --name auto-dev-server auto-dev-server
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

