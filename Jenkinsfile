pipeline {
    agent any

    tools{
	jdk 'A408_BE_Build'
    }

    enviroment{
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

                        sh 'echo manual Auto CI Start'
                        env.JSON_DATA = sh(script: 'curl --header "PRIVATE-TOKEN: 3WmDBHzqBCQzswYaBMp5" "https://lab.ssafy.com/api/v4/projects/507757/jobs"', returnStdout: true).trim()

                    // JSON 데이터에서 6번째 객체의 id 값 추출
                    def id_6th = sh(script: "echo '''${env.JSON_DATA}''' | jq -r '.[5].id'", returnStdout: true).trim()
                    echo "6번째 객체의 id: ${id_6th}"

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

