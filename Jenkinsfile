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
                        //secret.yml 가져오기
                       def credentialId = 'secretbe'
                    // withCredentials 블록을 사용하여 Secret 파일을 가져옴
                    withCredentials([file(credentialsId: credentialId, variable: 'SECRET_FILE')]) {
                        // SECRET_FILE 변수를 사용하여 작업 수행
                        sh 'ls -l'
                        sh 'echo "Secret File Content: ${SECRET_FILE}" >> src/main/resources/secret.yml'
                        
                    }



                        sh './gradlew clean build'   
                        sh 'jq --version'
                        sh 'cd build/libs && ls -al'

                        sshPublisher(
                            publishers: [
                                sshPublisherDesc(
                                    configName: 'ssafyhelper', // Jenkins 시스템 설정에서 SSH 설정을 추가한 이름
                                    transfers: [
                                        sshTransfer(
                                            execCommand: 'echo date >> logs.txt', // 원격 디렉토리 초기화
                                            sourceFiles: 'build/libs/goumunity-0.0.1-SNAPSHOT.jar', // 전송할 파일 경로 또는 패턴
                                            removePrefix: '/build/libs', // 원격 서버에 복사할 때 제거할 경로 접두사
                                            remoteDirectory: '/sendData' // 원격 서버에 전송할 디렉토리
                                        )
                                    ]
                                )
                            ]
                        )


                        sh 'echo manual Auto CI Start'
                        sh 'curl "https://ssafyhelper.shop/control/dev/be"'

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

