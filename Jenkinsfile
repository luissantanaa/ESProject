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
                sh "rm -r BodyTracking/BodyTracking/target/* || true"
                sh "cd BodyTracking/BodyTracking/ && mvn clean -Dmaven.test.skip package "
            }
                
        }
        stage('Test Analysis System') {
		    when{
                branch 'development'
            }
            agent{
                docker{
                    image 'maven:3-alpine'
                    args '-v $HOME/.m2:/root/.m2'
                }
            }
            steps {
                echo 'Testing phase for Analysis is about to start'
                sh 'cd BodyTracking/BodyTrackingAnalysis/ && mvn verify'
            }
        }

        stage('Test BT') {
		    when{
                branch 'development'
            }
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
            when{
                branch 'master'          
            }
            agent any
            steps{
                echo "deploying Body Tracking..."
                sh "cd BodyTracking/BodyTracking/ && mvn -f pom.xml -s ../settings.xml deploy"

                echo "deploying Body Tracking Analysis System..."

                sh "cd BodyTracking/BodyTracking/Analysis && mvn -f pom.xml -s ../settings.xml deploy"
		        
                echo "deploying..."
            }

        }
        
        stage('Clean Registry'){
            when{
                branch 'master'          
            }
            agent any
            steps{
                sh 'docker image rm 192.168.160.103:5000/p21/esp21bodytracking_build:latest || echo "Registry cleaned"'

                sh 'docker image rm 192.168.160.103:5000/p21/esp21bodytracking_as_build:latest || echo "Registry cleaned"'
            }
        }
        
        
        stage('DeployAS') {
            when{
                branch 'master'          
            }
            
            agent any
            steps {
                //Using docker registry to save docker image
                
                sh  '''
                        docker build -f BodyTracking/BodyTrackingAnalysis/Dockerfile -t esp21bodytracking_as_build:latest  .
                        docker tag  esp21bodytracking_as_build:latest 192.168.160.99:5000/p21/esp21bodytracking_as_build:latest
                        docker push 192.168.160.99:5000/p21/esp21bodytracking_as_build:latest                        
                        
                    '''
                sshagent (credentials: ['RuntimeVMCredP21']) {
                    sh '''
                        ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 docker rm -f esp21bodytrackingcontainer_as|| echo "container down"
                        ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 docker pull 192.168.160.99:5000/p21/esp21bodytracking_as_build:latest
                        ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 docker run -p 21000:21999 -d --name esp21bodytrackingcontainer_as esp21bodytracking_as_build:latest 
                    '''
                }
            
            }
        }

        stage('DeployBT') {
            when{
                branch 'master'          
            }
            
            agent any
            steps {
                //Using docker registry to save docker image
                
                sh  '''
                        docker build -f BodyTracking/BodyTracking/Dockerfile -t esp21bodytracking_build:latest  .
                        docker tag  esp21bodytracking_build:latest 192.168.160.99:5000/p21/esp21bodytracking_build:latest
                        docker push 192.168.160.99:5000/p21/esp21bodytracking_build:latest                        
                        
                    '''
                sshagent (credentials: ['RuntimeVMCredP21']) {
                    sh '''
                        ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 docker rm -f esp21bodytrackingcontainer_bt|| echo "container down"
                        ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 docker pull 192.168.160.99:5000/p21/esp21bodytracking_build:latest
                        ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 docker run -p 21000:21999 -d --name esp21bodytrackingcontainer_bt esp21bodytracking_build:latest 
                    '''
                }
            
            }
        }


        stage("Clean WS"){
            agent any
            steps{
                
                cleanWs()

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
