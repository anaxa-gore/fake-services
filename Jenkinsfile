pipeline {
    agent any
    tools {
        maven 'm3'
    }

    stages {
        stage('Build & Unit Tests') {
            steps {
                sh 'echo ----------------------------------------------- fake-services'

                // On signale le début des Tests
                rocketSend channel: 'fake-services', message: 'Début des build/tests'

                sh 'mvn clean'
                sh 'mvn install -P{params.PLATFORM_TO_BUILD}'
            }
            post {
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
                sh 'echo cool'

                // TODO TBA : collecter les résultats des tests
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