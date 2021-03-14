def project = 'isortegahlabs/poc-automation-testNG'
def branchApi = new URL("https://api.github.com/repos/${project}/branches")
def branchesRepo = new groovy.json.JsonSlurper().parse(branchApi.newReader())

def folderBase = "POCs"
def folderName = "pocTestNG"
folder(folderBase){
  description('Folder for projects POCs')
}

folder("${folderBase}/${folderName}"){
  description('Folder for project POC TestNG')
}

branchesRepo.each{
	def branchName = it.name

	def jobName = "${folderBase}/${folderName}/"+"${project}-${branchName}".replaceAll('/','-')
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
            remote { url('https://github.com/isortegahlabs/poc-automation-testNG') }
            branches(branchName)
            scriptPath('Jenkinsfile')
            extensions { }  
          }
        }
      }
    }

    parameters{
      stringParam('DOMAIN', 'localhost', 'Dominio de la URL de Selenium Hub')
    }

    }
}