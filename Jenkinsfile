pipeline {
    agent any

    stages {
        agent{
            docker{
                image 'maven:3-alpine'
                args '-v $HOME/.m2:/root/.m2'
            }
        }
        stage('Clean and Build') {
            steps {
                //clean maven project and create war file
                sh "cd BodyTracking/BodyTracking/ && mvn clean package "
                
        }
        stage('Test') {
            steps {
                echo "Tests Running"
            }
        }

        stage('Pre-Deploy'){
            steps{
                //send war and pom to artifact
                //sh "mvn deploy"
		echo "deploying..."
            }

        }

        stage('Deploy') {
            agent any
            sshagent (credentials: ['comoassim']) {
                steps {
                //scp DockerFile to runtime vm
                //scp war file to runtime vm
                //Execute commands to create and run docker container in the runtime vm
                 
                    
                sh '''    
                    scp DockerFile esp21@192.168.160.103:~
                    scp /BodyTracking/BodyTracking/target/BodyTracking-0.0.1-SNAPSHOT.war esp21@192.168.160.103:~
                    ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 docker build -t esp21BodyTrackingBuild .
                    ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 docker rm -f esp21BodyTrackingContainer || echo "container down"
                    ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 docker run -p 21 -d esp21BodyTrackingContainer esp21BodyTrackingBuild
                    ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 rm DockerFile
                    ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 rm war_file
                    
                    
                '''
                }
            }
        }
    }
}