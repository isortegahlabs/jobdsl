def project = 'isortegahlabs/jenkinsfiles_project'
def branchApi = new URL("https://api.github.com/repos/${project}/branches")
def branches = new groovy.json.JsonSlurper().parse(branchApi.newReader())
folder("project"){
    description('Folder for project A')
}

branches.each {
    def branchName = it.name
    def jobName = "project/"+"${project}-${branchName}".replaceAll('/','-')
    job(jobName) {
        triggers {
            cron('H/2 * * * *')
        }
        triggers {
            scm('H/1 * * * *')
        }
        scm {
            git("git://github.com/${project}.git", branchName)
        }
    }
}