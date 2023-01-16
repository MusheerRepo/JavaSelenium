# bt-ui-automation

This repo contains code of test automation suite.

## Parameters

To run tests below parameters are required:
- `URL` - target environment i.e. `dev-ui.brandtech.io`
- `USER` - email used for login to platform
- `PASSWORD` - password for above user
- `XML_SUITE` - test suite configuration, default is `Regression.xml`

## GCP usage

Tests are builded and executed in GCP project `bt-common`.

At the end of the test run, a notification is sent to `#release` Slack channel, notification contains link to test report.

### Build

Each time new change is pushed to `master` branch tests are builded, and result image is pushed to Artificats repository.
Trigger named `bt-ui-automation-merge` is used for that purpose.

History of builds and run is availabe [HERE](https://console.cloud.google.com/cloud-build/builds?hl=en-AU&orgonly=true&project=bt-common-001&supportedpurview=organizationId)

### Prerequisite for running suite

 - Automation suite requires few files (BMOForTestScript.mp4, DontUse1.jpg, DontUse2.mp4) to be present on environment as few tests use existing file, if these files are not found build will fail, although there is a automation step to upload these files before starting automation test run, but if incase it fails these files can be added manually using app, files can be found  [HERE](https://github.com/thebrandtechgroup/bt-ui-automation/tree/main/BrandTechUIAuto/src/main/java/com/qa/testdata)

### Running

Test run uses newest, successfully built images of tests. There are two way of triggering tests:
 - manual - by using `bt-ui-automation-run` trigger
 - via webhook - by calling webhook, CURL snippet that may be used for that purpose is below:

```
 curl --location --request POST 'https://cloudbuild.googleapis.com/v1/projects/bt-common-001/triggers/bt-ui-automation-run-webhook:webhook?key=AIzaSyB6Kbm6BZStzkL1DR6ewQYVXiKv09KL9tE&secret=secret123' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "url": "https://dev-ui.brandtech.io/",
        "user": "user email",
        "password": "user password",
        "xml_suite": "Regression.xml"
    }'
```

### Test results and reports
- Once execution is completed test results will be notified in `ext-release` channel, where we can see the info whether test ran or not, if ran then what's the pass/fail count, and extent report.
- The same results will be updated in TestRails test run of which ID has be given in curl command

### Reason for build failure
- When there are test failures
- When prerequisite test data is not found on environment and automated upload of prerequisite test data failed
- When prerequisite test data is found, but is not reprocessed using automation

#### What to do when build fails because of pre execution errors
- If build fails because of `Testdata check failure`, add prerequisite test data manually using app, files can be found  [HERE](https://github.com/thebrandtechgroup/bt-ui-automation/tree/main/BrandTechUIAuto/src/main/java/com/qa/testdata)
- If build fails because of `Reprocessing of existing file failure`, then check whether reprocess function is working fine or not

## Local bulding & Running

### Building
```sh
cd BrandTechUIAuto
docker build -t bt-automation .
```
### Runing

```sh
docker run -e "URL=https://dev-ui.brandtech.io/" -e "USER=<FILL>" -e  "PASSWORD=<FILL>" -e "XML_SUITE=Regression.xml" -e "TESTRAIL_CONFIG={\"username\":\"<FILL>\",\"password\":\"<FILL>\",\"url\":\"https://brandtech.testrail.io/\"}" -v $(pwd)/output:/app/test-output --rm bt-automation
```
