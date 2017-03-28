pipeline {
    agent any

    stages {
        stage('Tag') {
            when {
                expression {
                    BRANCH_NAME.startsWith('RELEASE_')
                }
            }
            steps {
                sh 'echo ON RELEASE !!!'
            }
        }

        stage('Build & Unit Tests') {
            steps {
                parallel(
                        build: {
                            // On signale le début des Tests
                            rocketSend channel: 'fake-services', message: 'Début du build'

                            withMaven(maven: 'M3') {
                                // On nettoie
                                sh 'mvn clean'
                                // On compile et on install en exécutant les tests unitaires
                                sh 'mvn install'
                            }
                        },
                        sonar: {
                            // TODO TBA : Uniquement sur develop ?
                            withSonarQubeEnv('sonarScanner') {
                                sh "${scannerHome}/bin/sonar-scanner"
                            }
                        }
                )
            }
            post {
                always {
                    // On sauvegarde systématiquement le rapport de résultat des tests
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

        stage('Deploy') {
            // On ne déploie que si on est sur la branche develop
            when {
                branch 'develop'
            }

            steps {
                rocketSend channel: 'fake-services', message: 'Déploiement dans NEXUS'

                // On a besoin des credentials contenus dans le fichier de config maven
                withMaven(maven: 'M3', globalMavenSettingsConfig: 'globalMaven') {
                    sh 'mvn deploy'
                }
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

        stage('Installation') {
            when {
                branch 'develop'
            }
            steps {
                sh 'echo Installation'
            }
        }
    }
}