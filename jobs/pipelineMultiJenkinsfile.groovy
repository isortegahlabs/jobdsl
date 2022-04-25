def project = 'isortegahlabs/jenkinsfiles_project'
def branchApi = new URL("https://api.github.com/repos/${project}/branches")
def branchesRepo = new groovy.json.JsonSlurper().parse(branchApi.newReader())

def folderName = "pipelineMultiJenkinsfile"
folder(folderName){
  description('Folder for project Pipeline simple')
}

branchesRepo.removeAll{
  it.name.contains("shared")
}

branchesRepo.removeAll{
  it.name.contains("master") 
}

branchesRepo.each{
	def branchName = it.name

	def jobName = "${folderName}/"+"${project}-${branchName}".replaceAll('/','-')
    pipelineJob(jobName) {

    //triggers {
      //scm('H/5 * * * *')
    //}

    //triggers {
      //cron('H/5 * * * *')
    //}

    description("Pipeline for $jobName")

    definition {
      cpsScm {
        scm {
          git {
            remote { url('https://github.com/isortegahlabs/jenkinsfiles_project') }
            branches(branchName)
            scriptPath('Jenkinsfile')
            extensions { }  // required as otherwise it may try to tag the repo, which you may not want
          }

          // the single line below also works, but it
          // only covers the 'master' branch and may not give you
          // enough control.
          // git(repo, 'master', { node -> node / 'extensions' << '' } )
        }
      }
    }
    }
}