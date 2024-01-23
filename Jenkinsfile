pipeline {
    agent any

    tools{
	jdk 'A408_BE_Build'
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
                        sh 'json_data = curl --header "PRIVATE-TOKEN: 3WmDBHzqBCQzswYaBMp5" "https://lab.ssafy.com/api/v4/projects/507757/jobs"'
                        sh 'echo json_data'

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

