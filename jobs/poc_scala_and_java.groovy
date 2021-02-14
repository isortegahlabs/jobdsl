def folderName = "pocScalaAndJava"
folder(folderName){
  description('Folder for project Scala And Java')
}

def jobName = "${folderName}/poc_Scala_And_Java"

pipelineJob(jobName) {
    definition {
        cps {
            script('''
                import groovy.transform.Field

                node('bash') {
                    try{
                        String urlLastRelease = "https://api.github.com/repos/isortegahlabs/scala_and_java/releases/latest"
                        
                                    
                        try {
                            withCredentials([usernamePassword(credentialsId:'isortegahlabs-token',
                                usernameVariable: 'USERNAME',
                                passwordVariable:'TOKEN')]){
                            String CMD = "curl -H 'Accept: application/vnd.github.v3+json' " +
                                    "-u " + USERNAME +":" + TOKEN + " -LJ ${urlLastRelease} " +
                                    "| egrep browser_download_url |cut -d : -f 2,3 " +
                                    '| tr -d \\\\\\"' +
                                    "| wget -qi - --no-check-certificate -O scala_and_java.jar "
                                sh "java -version"
                                sh CMD
                                sh "java -cp scala_and_java.jar me.isortegah.pocs.App"
                                }
                        
                        } catch (Exception e) {
                            error "Error executing RepositoryObjects: ${e.getMessage()}."
                        }

                    } finally {
                        cleanWs()
                    }
                }
            '''.stripIndent())
            sandbox(false)
        }
    }

}