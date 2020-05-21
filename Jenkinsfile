pipeline {
    agent any
    stages {
        
        stage('Build BT') {
            agent{
                docker{
                    image 'maven:3-alpine'
                    args '-v $HOME/.m2:/root/.m2'
                }
            }
            steps {
                //clean maven project and create war file
                sh "cd BodyTracking/BodyTracking/ && mvn clean -Dmaven.test.skip package "
                echo "Body Tracking System built"
            }
                
        }
        stage('Build Analysis') {
            agent{
                docker{
                    image 'maven:3-alpine'
                    args '-v $HOME/.m2:/root/.m2'
                }
            }
            steps {
               sh "cd BodyTracking/BodyTrackingAnalysis/ && mvn clean -Dmaven.test.skip package "
               echo "Analisys System built"
	       sh "cd BodyTracking/BodyTrackingAnalysis/target/ && ls "
               
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

                sh "cd BodyTracking/BodyTrackingAnalysis && mvn -f pom.xml -s ../settings.xml deploy"
		        
                echo "deploying..."
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
			cd BodyTracking/BodyTrackingAnalysis/ && docker build -f BodyTracking/BodyTrackingAnalysis/Dockerfile -t esp21bodytracking_as_build:latest  .
                        docker tag  esp21bodytracking_as_build:latest 192.168.160.99:5000/p21/esp21bodytracking_as_build:latest
                        docker push 192.168.160.99:5000/p21/esp21bodytracking_as_build:latest                        
                        
                    '''
                sshagent (credentials: ['RuntimeVMCredP21']) {
                    sh '''
                        ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 docker rm -f esp21bodytrackingcontainer_as|| echo "container down"
                        ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 docker pull 192.168.160.99:5000/p21/esp21bodytracking_as_build:latest
                        ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 docker run -p 21001:21001 -d --name esp21bodytrackingcontainer_as 192.168.160.99:5000/p21/esp21bodytracking_as_build:latest 
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
                        cd BodyTracking/BodyTracking/ &&docker build -f BodyTracking/BodyTracking/Dockerfile -t esp21bodytracking_build:latest  .
                        docker tag  esp21bodytracking_build:latest 192.168.160.99:5000/p21/esp21bodytracking_build:latest
                        docker push 192.168.160.99:5000/p21/esp21bodytracking_build:latest                        
                        
                    '''
                sshagent (credentials: ['RuntimeVMCredP21']) {
                    sh '''
                        ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 docker rm -f esp21bodytrackingcontainer_bt|| echo "container down"
                        ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 docker pull 192.168.160.99:5000/p21/esp21bodytracking_build:latest
                        ssh -o StrictHostKeyChecking=no esp21@192.168.160.103 docker run -p 21000:21000 -d --name esp21bodytrackingcontainer_bt 192.168.160.99:5000/p21/esp21bodytracking_build:latest 
                    '''
                }
            
            }
        }

    }
    post {
        always{
            cleanWs()
            echo 'workspace cleaned'
        }
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
