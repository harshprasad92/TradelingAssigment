# TradelingAssignment
# Pre requisits to execute the project on any machine/server
1. Execution Machine/Server should have JRE 1.7 or greater
2. Execution Machine/Server should have maven
3. Execution Machine/Server should have node installed. Guide to install node.js: https://nodejs.org/en/download/package-manager/
4. Execution Machine/Server should have command line appium installed. To install check link: https://appium.io/docs/en/about-appium/getting-started/?lang=en
5. Execution Machine/Server should have android environment. Steps to setup android environment: https://developer.android.com/studio/command-line/variables
6. A real android device or an android emulator

# To execute the Project:
1. Connect an android device or tart and emulator. On terminal run 'adb devices'. This will give the device Id of your device.
2. Open file 'TradelingAssigment/src/main/resources/setup/mobileConfig.properties'.
3. Set the path for appium.js, node.js, ANDROID_HOME in the keys appiumJsPath, nodeJsPath, androidHome rescpectively
4. If Amazon Shopping app is installed on the device then set value for key 'isAppInstalled' as true
5. If app is not installed set the value for key 'isAppInstalled' as false and assign the path to the Amazon apk to key 'appBuildPath' and name of the apk to the key 'appBuildName'.
6. Assign the device ID fetched from command 'adb devices' to the key 'deviceId'. And the name of the device to the key 'deviceName'
7. Open file 'pathToProjectRootFolder/TradelingAssigment/src/test/resources/features/Amazon.feature' and tag the scenarios with tag '@Regression' which are required to be executed
8. Once all of the above is done, under the project root folder execute the commmand 'mvn install' for first time and then 'mvn clean test' for every subsequent test
9. The reports after every execution can be viewed at URL: 'pathToProjectRootFolder/TradelingAssigment/target/cucumber-reports/report.html'

# Documents
1. Bugs are reported under file 'pathToProjectRootFolder/TradelingAssigment/src/test/resources/testDocuments/Amazon_tradelingAssigment_bugs.docx'
2. To view a sample execution report download folder 'pathToProjectRootFolder/TradelingAssigment/src/test/resources/testDocuments/TestExecutionReport'. And view paste the url 'pathTofolder/TradelingAssigment/src/test/resources/testDocuments/TestExecutionReport/report.html'
