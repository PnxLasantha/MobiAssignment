# MobiAssignment [![CircleCI](https://circleci.com/gh/PnxLasantha/MobiAssignment/tree/master.svg?style=svg&circle-token=1d3b561ce32b809871b71e2f15211b8e284a8047)](https://circleci.com/gh/PnxLasantha/MobiAssignment/tree/master)

MobiQuity Assignment

## Configuring Allure Reports

This is not necessery to run the automation.This section is only need to generate and view ther allure reports

### Windows

1.Open a powershell window and install scoop using the following command `iwr -useb get.scoop.sh | iex` ( This will
install scoop package manager for windows)

2.Install allure using `scoop install allure`

3.Verify allure installation `allure --version`

Note - For mac please refer this document to configure

## Running the tests

This is a Maven project. The tests can be executed from the command line using `mvn clean test`

Execute the following command in the terminal to generate the
reports `allure serve <location of the allure-results folder>` (This allure-results folder is created after executing
the tests)

## Tools used

- Rest Assured - Version 5.0
- TestNG - Testing framework Version 7.4
- Allure - Reporting plugin Version 2.17
- Java - Version 17.0.2
- Json - Validate Jason schema Version 20220320
- Commons Validator - Validate emails Version 1.7

## Sample Test result

