pipeline {
    agent any
    tools {
        maven 'm3'
    }

    stages {
        environment {
            ROCKET_CHANNEL = 'fake-services'
        }

        stage('Build & Unit Tests') {
            steps {
                // On signale le début des Tests
                rocketSend channel: '$ROCKET_CHANNEL', message: 'Début des build/tests'

                sh 'mvn clean'
                sh 'mvn install -P{params.PLATFORM_TO_BUILD}'
            }
            post {
                success {
                    rocketSend(
                            attachments: [[color: 'green', text: 'Build & Tests OK', title: 'Fin']],
                            channel: '${ROCKET_CHANNEL}',
                            message: 'Fin',
                            rawMessage: true
                    )
                }
                unstable {
                    rocketSend(
                            attachments: [[color: 'red', text: 'Tests KO', title: 'Problème durant les tests, vérifier le log console']],
                            channel: '${ROCKET_CHANNEL}',
                            message: 'Fin'
                    )
                }
                failure {
                    rocketSend(
                            attachments: [[color: 'red', text: 'Tests KO', title: 'Echec du build, vérifier le log console']],
                            channel: '${ROCKET_CHANNEL}',
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
                rocketSend channel: '${ROCKET_CHANNEL}', message: 'Déploiement dans NEXUS'

                sh 'mvn deploy'
            }
            post {
                success {
                    rocketSend(
                            attachments: [[color: 'green', text: 'Déploiement OK', title: 'Fin déploiement']],
                            channel: '${ROCKET_CHANNEL}',
                            message: 'Fin',
                            rawMessage: true
                    )
                }
                failure {
                    rocketSend(
                            attachments: [[color: 'red', text: 'Déploiement KO, vérifier le log console', title: 'Echec du build']],
                            channel: '${ROCKET_CHANNEL}',
                            message: 'Fin'
                    )
                }
            }
        }
    }
}