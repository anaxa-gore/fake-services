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

        stage('Build, Tests & Qualité') {
            steps {

                // On signale le début des Tests
//                rocketSend channel: 'fake-services', message: 'Début du build / tests / qualité'
                mattermostSend color: "good", message: "Début du build / tests / qualité"

                withMaven(maven: 'M3') {
                    // On nettoie
                    sh 'mvn clean'
                    // On compile et on install en exécutant les tests unitaires
                    sh 'mvn install'

                    withSonarQubeEnv('SonarApave') {
                        // On copie toutes les dépendances dans target/dependency pour que sonar puisse réaliser
                        // correctement la couverture de code.
                        sh 'mvn dependency:copy-dependencies'

                        // On lance la couverture de code
                        sh "${tool(name: 'sonarJ', type: 'hudson.plugins.sonar.SonarRunnerInstallation')}/bin/sonar-scanner"
                    }
                }
            }
            post {
                always {
                    // On sauvegarde systématiquement le rapport de résultat des tests
                    junit 'target/surefire-reports/TEST-*.xml'
                }
                success {
//                    rocketSend(
//                            attachments: [[color: 'green', text: 'Build & Tests OK', title: 'Fin']],
//                            channel: 'fake-services',
//                            message: 'Fin',
//                            rawMessage: true
//                    )
                    mattermostSend color: "good", message: "Build & Tests OK"
                }
                unstable {
//                    rocketSend(
//                            attachments: [[color: 'red', text: 'Tests KO', title: 'Problème durant les tests, vérifier le log console']],
//                            channel: 'fake-services',
//                            message: 'Fin'
//                    )
                    mattermostSend color: "danger", message: "Problème durant les tests, vérifier le log console\n(<${env.BUILD_URL}>)"
                }
                failure {
//                    rocketSend(
//                            attachments: [[color: 'red', text: 'Tests KO', title: 'Echec du build, vérifier le log console']],
//                            channel: 'fake-services',
//                            message: 'Fin'
//                    )
                    mattermostSend color: "danger", message: "Echec durant le build, vérifier le log console\n(<${env.BUILD_URL}>)"
                }
            }
        }

        stage('Deploy') {
            // On ne déploie que si on est sur la branche develop
            when {
                branch 'develop'
            }

            steps {
//                rocketSend channel: 'fake-services', message: 'Déploiement dans NEXUS'
                mattermostSend color: "good", message: "Déploiement dans NEXUS"

                // On a besoin des credentials contenus dans le fichier de config maven
                withMaven(maven: 'M3', globalMavenSettingsConfig: 'globalMaven') {
                    sh 'mvn deploy -DskipTests'
                }
            }
            post {
                success {
//                    rocketSend(
//                            attachments: [[color: 'green', text: 'Déploiement OK', title: 'Fin déploiement']],
//                            channel: 'fake-services',
//                            message: 'Fin',
//                            rawMessage: true
//                    )
                    mattermostSend color: "good", message: "Déploiement NEXUS OK"
                }
                failure {
//                    rocketSend(
//                            attachments: [[color: 'red', text: 'Déploiement KO, vérifier le log console', title: 'Echec du build']],
//                            channel: 'fake-services',
//                            message: 'Fin'
//                    )
                    mattermostSend color: "danger", message: "Echec durant le déploiement, vérifier le log console\n(<${env.BUILD_URL}>)"
                }
            }
        }

        stage('Installation') {
            when {
                branch 'develop'
            }
            steps {
                sh 'echo Installation sur environnement de développement'
            }
        }
    }
}