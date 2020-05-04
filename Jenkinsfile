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
        
        stage('Clean Registry'){
            agent any
            steps{
                sh 'docker image rm 192.168.160.103:5000/p21/esp21bodytrackingbuild:latest || echo "Registry cleaned"'
            }
        }
        
        
        stage('Deploy') {
            agent any
            steps {
                //Using docker registry to save docker image
                
                sh  '''
                        docker build -t esp21bodytracking_build:latest  .
                        docker tag  esp21bodytracking_build:latest 192.168.160.99:5000/p21/esp21bodytracking_build:latest
                        docker push 192.168.160.99:5000/p21/esp21bodytracking_build:latest                        
                        
                    '''
                sshagent (credentials: ['RuntimeVMCredP21']) {
                    sh '''
                        ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 docker rm -f esp21bodytrackingcontainer_r|| echo "container down"
                        ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 docker pull 192.168.160.99:5000/p21/esp21bodytracking_build:latest
                        ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 docker run -p 21000:21999 -d --name esp21bodytrackingcontainer_r esp21bodytracking_build:latest 
                    '''
                }
            
            }
        }


        stage("Clean WS"){
            agent any
            steps{
                
                sh 'cleanWs()'

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
