pipeline {
    agent any
    tools {
        maven 'm3'
    }
    environnement {
        ROCKET_CHANNEL = 'fake-services'
    }

    stages {
        stage('Build & Unit Tests') {
            steps {
                // On signale le début des Tests
                rocketSend channel: 'ic_services', message: 'Début des build/tests'

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
                            attachments: [[color: 'red', text: 'Tests KO', title: 'Problème durant les tests']],
                            channel: '${ROCKET_CHANNEL}',
                            message: 'Fin',
                            rawMessage: true
                    )
                }
                failure {
                    rocketSend(
                            attachments: [[color: 'red', text: 'Tests KO', title: 'Echec du build']],
                            channel: '${ROCKET_CHANNEL}',
                            message: 'Fin',
                            rawMessage: true
                    )
                }
            }
        }

        stage('Tests results') {
            sh 'echo cool'

            // TODO TBA : collecter les résultats des tests
        }

        stage('Deploy') {
            rocketSend channel: 'ic_services', message: 'Déploiement dans NEXUS'

            sh 'mvn deploy'
        }
    }
}