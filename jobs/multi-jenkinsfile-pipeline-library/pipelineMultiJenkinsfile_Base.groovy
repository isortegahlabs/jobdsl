def project = 'isortegahlabs/multi-Jenkinsfile-pipeline-library'
def branchApi = new URL("https://api.github.com/repos/${project}/branches")
def branchesRepo = new groovy.json.JsonSlurper().parse(branchApi.newReader())

def folderName = "pipelineMultiJenkinsfiles"
folder(folderName){
  description('Folder for project Pipeline Multi Jenkinsfile')
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
            remote { url('https://github.com/isortegahlabs/multi-Jenkinsfile-pipeline-library') }
            branches(branchName)
            scriptPath('base.Jenkinsfile')
            extensions { }  // required as otherwise it may try to tag the repo, which you may not want
          }
        }
      }
    }
    }
}