
/**
 * Shared Library: mainBranchMavenPipeline
 * Description: Jenkins pipeline for building/deploying main branch of a Maven app.
 * Parameters:
 *   - repo (optional): Git URL to clone. Defaults to mavenwebapp-demo-app.
 */
def call(Map config = [:]) {
    pipeline {
        agent any
        tools {
            maven 'maven3.9.9'
        }
        stages {
            stage('Clone Code') {
                steps {
                    git branch: 'main', url: config.get('repo', 'https://github.com/m-pasima/maven-web-app-demo.git')
                }
            }
            stage('Maven Build') {
                steps {
                    sh "mvn clean package"
                }
            }
            stage('Sonar Scan') {
                steps {
                    sh "mvn verify sonar:sonar"
                }
            }
            stage('Upload Build Artifacts') {
                steps {
                    configFileProvider([configFile(fileId: '6f1e7ff0-b3ac-4879-9134-020fc8c49934', variable: 'MAVEN_SETTINGS')]) {
                        sh 'mvn deploy --settings $MAVEN_SETTINGS'
                    }
                }
            }
            stage('Deploy WAR Application') {
                steps {
                    deploy adapters: [
                        tomcat9(
                            alternativeDeploymentContext: '', 
                            credentialsId: 'tomcat-creds', 
                            path: '', 
                            url: 'http://18.130.11.10:8080/'
                        )
                    ], contextPath: 'tesco', war: '**/*.war'
                }
            }
        }
        post { 
            always { 
                script {
                    def COLOR_MAP = [
                        'SUCCESS': 'good',
                        'FAILURE': 'danger',
                        'UNSTABLE': 'warning',
                        'ABORTED': '#cccccc'
                    ]
                    slackSend(
                        color: COLOR_MAP[currentBuild.currentResult] ?: 'danger',
                        channel: '##all-devops-academy',
                        message: "Build done by Pasima. JOB: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
                    )
                }
            }
        }
    }
}
