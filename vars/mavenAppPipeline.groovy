def call(Map config = [:]) {
    pipeline {
        agent any
        tools { maven 'maven3.9.9' }
        stages {
            stage('Clone code') {
                steps {
                    git branch: env.BRANCH_NAME, url: config.repo
                }
            }
            stage('Build') {
                steps { sh 'mvn clean package' }
            }
            stage('scan with sonarqube') {
                steps { sh 'mvn verify sonar:sonar' }
            }
            stage('Upload Build Artifacts') {
                steps { sh 'mvn deploy' }
            }
            stage('Deploy to tomcat') {
                steps {
                    script {
                        if (env.BRANCH_NAME in ['main', 'staging']) {
                            deploy adapters: [
                                tomcat9(
                                    credentialsId: 'tomcat-creds',
                                    url: 'http://18.134.158.50:8080'
                                )
                            ], contextPath: 'tesco', war: '**/*.war'
                        } else {
                            echo 'Skipping deployment for dev branch'
                        }
                    }
                }
            }
        }
    }
}
