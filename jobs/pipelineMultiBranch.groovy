def project = 'isortegahlabs/jenkinsfiles_project'

def folderName = "SharedLibraries"
folder(folderName){
  description('Folder for project A')
}
multibranchPipelineJob("${folderName}/baseProject"){
  
  displayName("SharedLibraries Project")
  description("Proyecto de prueba SharedLibraries")
  
  factory {
    workflowBranchProjectFactory {
      scriptPath('Jenkinsfile')
    }
  }

  orphanedItemStrategy {
    discardOldItems {
      numToKeep(5)
      daysToKeep(2)
    }
  }

  branchSources {
    git {
      id('shared') 
      remote("https://github.com/${project}")
      //credentialsId('github-ci')
      includes("feature/sha* feature/sharedLibraries_*")
    }
    /*git {
      id('shareds') 
      remote('https://github.com/isortegahlabs/jenkinsfiles_project')
      //credentialsId('github-ci')
      includes('feature/basi*')
    }*/
    /*
    branchSource {
        source {
          bitbucket {
            credentialsId("top-secret-1234-some-guid")
            repoOwner("${bitbucket_project.toUpperCase()}")
            repository("${bitbucket_repo}")
            serverUrl("https://bitbucket.acme.com/")
            traits {
              headWildcardFilter {
                includes("master release/* feature/* bugfix/*")
                excludes("")
              }
            }
          }
        }
    */
  }

  triggers {
    cron('H/5 * * * *')
  }

}

