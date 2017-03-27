pipeline {
    agent any

    stages {
        stage('Build & Unit Tests') {
            steps {
                // On signale le début des Tests
                rocketSend channel: 'fake-services', message: 'Début du build'

                withMaven(maven: 'M3', globalMavenSettingsConfig: 'globalMaven') {
                    // On nettoie
                    sh 'mvn clean'
                    // On compile et on install en exécutant les tests unitaires
                    sh 'mvn install'
                }
            }
            post {
                always {
                    // On sauvegarde systématiquement le rapportde résultat des tests
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

        stage('Archivage du build') {
            steps {
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