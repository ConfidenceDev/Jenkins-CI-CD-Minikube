pipeline {
    agent any
    triggers {
        githubPush()
    }
    tools{
        maven 'maven'
    }
    stages{
        stage('Build Maven'){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/ConfidenceDev/Jenkins-CI-CD-Minikube']]])
                sh 'mvn clean install'
            }
        }
        stage('Build docker image'){
            steps{
                script{
                    sh 'docker build -t confidencedev/cicd .'
                }
            }
        }
        stage('Push image to Hub'){
            steps{
                script{
                   withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                        sh 'docker login -u confidencedev -p ${dockerhubpwd}'
                   }
                   sh 'docker push confidencedev/cicd'
                }
            }
        }
        stage('SonarQube analysis'){
            steps{
                withSonarQubeEnv(credentialsId: 'sonar'){
                    sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=cicd -Dsonar.host.url=http://localhost:9000 -Dsonar.login=${sonar}'
                }
            }
        }
        stage('Deploy to k8s'){
            steps{
                script{
                    kubernetesDeploy (configs: 'deployment.yaml',kubeconfigId: 'k8sconfigpwd')
                }
            }
        }
    }
}