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

