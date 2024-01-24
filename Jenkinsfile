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

