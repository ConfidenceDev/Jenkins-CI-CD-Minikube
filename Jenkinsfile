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
                withSonarQubeEnv('sonar'){
                    sh "mvn clean install sonar:sonar"
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