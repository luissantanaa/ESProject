pipeline {
    agent any
    stages {
        
        stage('Clean and Build') {
            agent{
                docker{
                    image 'maven:3-alpine'
                    args '-v $HOME/.m2:/root/.m2'
                }
            }
            steps {
                //clean maven project and create war file
                sh "rm -r BodyTracking/BodyTracking/target/*"
                sh "cd BodyTracking/BodyTracking/ && mvn clean -Dmaven.test.skip package "
            }
                
        }
        stage('Test') {
            agent{
                docker{
                    image 'maven:3-alpine'
                    args '-v $HOME/.m2:/root/.m2'
                }
            }
            steps {
                echo 'Testing phase is about to start'
                sh 'cd BodyTracking/BodyTracking/ && mvn verify'
            }
        }

        stage('Pre-Deploy'){
            agent any
            steps{
                //send war and pom to artifact
                sh "mvn -f BodyTracking/BodyTracking/pom.xml -s BodyTracking/settings.xml deploy"
		        echo "deploying..."
            }

        }
        /*
        stage('Clean Registry'){
            agent any
            steps{
                sh 'docker image rm 192.168.160.103:5000/p21/esp21bodytrackingbuild:latest || echo "Registry cleaned"
            }
        }
        
        */
        stage('Deploy') {
            steps {
            //scp DockerFile to runtime vm
            //scp war file to runtime vm
            //Execute commands to create and run docker container in the runtime vm
                sshagent (credentials: ['RuntimeVMCredP21']) {
                    sh '''
                        echo "$(which scp)"
                        scp -o StrictHostKeyChecking=no Dockerfile esp21@192.168.160.103:~
                        scp -o StrictHostKeyChecking=no BodyTracking/BodyTracking/target/BodyTracking-0.0.2-SNAPSHOT.war esp21@192.168.160.103:~        
                    '''
                
                    sh '''    
                        ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 docker build -t esp21bodytrackingbuild:latest .
                        ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 docker rm -f esp21bodytrackingcontainer || echo "container down"
                        ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 docker run -p 21000:21999 -d --name esp21bodytrackingcontainer esp21bodytrackingbuild:latest
                        ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 rm Dockerfile
                        ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 rm BodyTracking-0.0.1-SNAPSHOT.war
                        
                        
                    '''
                }

                /*

                //Using docker registry to save docker image
                agent any
                sh  '''
                        docker build -t esp21bodytrackingBuild:latest  .
                        docker tag  esp21bodytrackingcontainer:latest 192.168.160.99:5000/p21/esp21bodytrackingBuild:latest
                        docker push esp21bodytrackingcontainer:latest                        
                        
                    '''
                sshagent (credentials: ['RuntimeVMCredP21']) {
                    sh '''
                        ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 docker rm -f esp21bodytrackingcontainerR || echo "container down"
                        ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 docker pull 192.168.160.99:5000/p21/esp21bodytrackingcontainer:latest
                        ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 docker run -p 21000:21999 -d --name esp21bodytrackingcontainerR esp21bodytrackingcontainer:latest 
                    '''
                }
                */
            }
        }
    }
    post {
        success {
            echo 'Pipeline was successful'
        }
        failure {
            echo 'Pipeline failed'
        }
        unstable {
            echo 'Pipeline was marked as unstable'
        }
        changed {
            echo 'Pipeline state changed'
        }
    }
}
