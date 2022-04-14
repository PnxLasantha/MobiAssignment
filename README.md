# Mobiquity Assignment [![CircleCI](https://circleci.com/gh/PnxLasantha/MobiAssignment/tree/master.svg?style=svg&circle-token=1d3b561ce32b809871b71e2f15211b8e284a8047)](https://circleci.com/gh/PnxLasantha/MobiAssignment/tree/master)

Detailed Test plan is found in Mobiquity Management Plan.pdf 

## Configuring Allure Reports

This is not necessery to run the automation.This section is only need to generate and view allure reports locally.

### Windows

1.Open a powershell window and install scoop using the following command `iwr -useb get.scoop.sh | iex` ( This will
install scoop package manager for windows)

2.Install allure using `scoop install allure`

3.Verify allure installation `allure --version`

Note - For mac please refer this [document](https://docs.qameta.io/allure/) to configure

## Running the tests

This is a Maven project. The tests can be executed from the command line using `mvn clean test -DsuiteXmlFile=testSuits/Assignment.xml`

Execute the following command in the terminal to generate the
reports `allure serve <location of the allure-results folder>`Eg: `allure serve "C:\Assignment\allure-results"` (This allure-results folder is created in the project after executing
the tests)

## Tools used

- Rest Assured - Version 5.0
- TestNG - Testing framework Version 7.4
- Allure - Reporting plugin Version 2.17
- Java - Version 17.0.2
- Json - Validate Jason schema Version 20220320
- Commons Validator - Validate emails Version 1.7

## Sample Test result

<img src="https://i.ibb.co/2kFTH4J/tr.png"
     alt="Markdown Monster icon"
     style="float: left; margin-right: 10px;" />
