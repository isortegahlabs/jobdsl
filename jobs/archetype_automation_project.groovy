def project = 'isortegahlabs/archetype-automation-project'

def folderName = "Archetype"
folder(folderName){
  description('Proyecto Archetype')
}
multibranchPipelineJob("${folderName}/base"){
  
  displayName("Archetype Project")
  description("Proyecto Archetype")
  
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
      id('master') 
      remote("https://github.com/${project}")
      credentialsId('isortegah')
      includes("*")
    }
  }
/*
  triggers {
    cron('H/5 * * * *')
  }*/

}

