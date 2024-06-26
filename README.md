# wine-web-automation
Automation framework for web applications


### Requirements
* JDK 21


### Features
* Support for Chrome and Firefox browsers
* CircleCI integration
* Parallel execution
* Rerun of failed test cases
* Failed scenario reporting in JIRA 
* Slack integration


### Usage
```
Execute run_tests.sh with the following parameters:
    -n, --browser_name        Browser on which the tests will be executed | Optional | Options: chrome/firefox | Default: chrome
    -t, --tag                 Test cases with this tag will be executed | Optional | Options: sanity/smoke etc. | Default: web
    -b, --build_env           Environment under test | Optional | Options: uat/preprod/prod | Default: uat
    -p, --parallel_run        Enable or disable parallel run of scenarios | Optional | Options: true/false | Default: false
    -j, --jira_reporting      Enable or disable creating JIRA tickets for failed scenarios | Optional | Options: true/false | Default: false
```


### Example 1
```
 > ./run_tests.sh -n=firefox -t=sanity -b=preprod -p=true -j=true
```
##### This command will:
* Run tests in **Firefox** browser
* Run scenarios that are tagged **'@sanity'**
* Run the tests on the url defined for **preprod** environment in Constants.java class
* Run the scenarios in **parallel** mode on different browser sessions
* JIRA tickets **will be created** for scenarios that fail in both main and rerun sessions


### Example 2
```
 > ./run_tests.sh
```
##### This commend is equivalent to:
```
 > ./run_tests.sh -n=chrome -t=web -b=uat -p=false -j=false
```
##### The above commands will:
* Run tests in **Chrome** browser
* Run scenarios that are tagged **'@web'**
* Run the tests on the url defined for **uat** environment in Constants.java class
* Run the scenarios in **serial** (non-parallel) mode on a single browser session
* JIRA tickets **will not be created** for scenarios that fail in both main and rerun sessions