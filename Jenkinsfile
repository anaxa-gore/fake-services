pipeline {
    agent any
    tools {
        maven 'm3'
    }

    stages {
        stage('Build & Unit Tests') {
            steps {
                // On signale le début des Tests
                rocketSend channel: 'fake-services', message: 'Début du build'

                sh 'mvn clean'
                sh 'mvn compile'
                sh 'mvn install -fae'
            }
            post {
                always {
                    junit 'target/surefire-reports/TEST-*.xml'
                }
                success {
                    rocketSend(
                            attachments: [[color: 'green', text: 'Build & Tests OK', title: 'Fin']],
                            channel: 'fake-services',
                            message: 'Fin',
                            rawMessage: true
                    )
                }
                unstable {
                    rocketSend(
                            attachments: [[color: 'red', text: 'Tests KO', title: 'Problème durant les tests, vérifier le log console']],
                            channel: 'fake-services',
                            message: 'Fin'
                    )
                }
                failure {
                    rocketSend(
                            attachments: [[color: 'red', text: 'Tests KO', title: 'Echec du build, vérifier le log console']],
                            channel: 'fake-services',
                            message: 'Fin'
                    )
                }
            }
        }

        stage('Tests results') {
            steps {
                junit 'target/surefire-reports/TEST-*.xml'
                archive 'target/*.war'
            }
        }

        stage('Deploy') {
            steps {
                rocketSend channel: 'fake-services', message: 'Déploiement dans NEXUS'

                sh 'mvn deploy'
            }
            post {
                success {
                    rocketSend(
                            attachments: [[color: 'green', text: 'Déploiement OK', title: 'Fin déploiement']],
                            channel: 'fake-services',
                            message: 'Fin',
                            rawMessage: true
                    )
                }
                failure {
                    rocketSend(
                            attachments: [[color: 'red', text: 'Déploiement KO, vérifier le log console', title: 'Echec du build']],
                            channel: 'fake-services',
                            message: 'Fin'
                    )
                }
            }
        }
    }
}