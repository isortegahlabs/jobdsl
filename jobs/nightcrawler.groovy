def project = 'isortegah/nightcrawler'

def folderName = "Nightcrawler"
folder(folderName){
  description('Proyecto Nightcrawler')
}
multibranchPipelineJob("${folderName}/baseProject"){
  
  displayName("Nightcrawler Project")
  description("Proyecto Nightcrawler")
  
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
      credentialsId('isortegah')
      includes("*")
    }
  }

  triggers {
    cron('H/5 * * * *')
  }

}

