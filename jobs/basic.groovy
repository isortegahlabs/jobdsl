def project = 'isortegahlabs/jenkinsfiles_project'
def branchApi = new URL("https://api.github.com/repos/${project}/branches")
def branches = new groovy.json.JsonSlurper().parse(branchApi.newReader())
branches.each {
    def branchName = it.name
    def jobName = "${project}-${branchName}".replaceAll('/','-')
    folder("project"){
        job(jobName) {
            scm {
                git("git://github.com/${project}.git", branchName)
            }
        }
    }
}