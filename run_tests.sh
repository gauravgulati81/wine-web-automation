#!/usr/bin/env bash

script_path="$(dirname $0)"

# Get command line arguments
for i in "$@"; do
  case $i in
  -n=* | --browser_name=*)
    browser_name="${i#*=}"
    echo "browser_name: ${browser_name}"
    shift
    ;;
  -t=* | --tag=*)
    run_tag="@${i#*=}"
    echo "run_tag: ${run_tag}"
    shift
    ;;
  -b=* | --build_env=*)
      build_env="${i#*=}"
      echo "build_env: ${build_env}"
      shift
      ;;
  -p=* | --parallel_run=*)
      parallel_run="${i#*=}"
      echo "parallel_run: ${parallel_run}"
      shift
      ;;
  -j=* | --jira_reporting=*)
      jira_reporting="${i#*=}"
      echo "jira_reporting: ${jira_reporting}"
      shift
      ;;
  --default)
    shift
    ;;
  *)
    # unknown option
    echo "Unknown option: '$i'"
    exit -1
    ;;
  esac
done


#If parameter does NOT exist with Value
if [ -z "$browser_name" ]; then
     browser_name="chrome"
fi
if [ -z "$run_tag" ]; then
     run_tag="@web"
fi
if [ -z "$build_env" ]; then
     build_env="uat"
fi
if [ -z "$parallel_run" ]; then
     parallel_run="false"
fi
if [ -z "$jira_reporting" ]; then
     jira_reporting="false"
fi

echo "Running Tests with the following Cucumber Options :"
echo "Browser: ${browser_name}"
echo "Run Tag : ${run_tag}"
echo "Build Env : ${build_env}"
echo "Parallel Run : ${parallel_run}"
echo "Log bugs in JIRA for failed scenarios : ${jira_reporting}"

if [ "$parallel_run" == "true" ]; then
  feature_path="src/test/resources/Features/ParallelRunTests"
else
  feature_path="src/test/resources/Features/SerialRunTests"
fi

mvn test -Dbrowser.name=${browser_name} -Dbuild.env=${build_env} -Dparallel.run=${parallel_run} "-Dcucumber.filter.tags=${run_tag}" "-Dcucumber.features=${feature_path}" -Drerun.flag="0"

if [ -s target/failedTests.txt ]; then
        echo "************************** Re-running failed tests **************************"
        mvn test -Dbrowser.name=${browser_name} -Dbuild.env=${build_env} -Dparallel.run=${parallel_run} "-Dcucumber.filter.tags=${run_tag}" "-Dcucumber.features=@target/failedTests.txt" -Drerun.flag="1" -Djira.reporting=${jira_reporting}
fi