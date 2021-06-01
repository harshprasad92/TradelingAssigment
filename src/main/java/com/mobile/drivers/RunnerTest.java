package com.mobile.drivers;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.cucumber.listener.Reporter;
import com.reporting.reports.Report;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features = { "src/test/resources/features" }, glue = "com.test.stepDefinition", format = {
		"html:target/cucumber-reports/cucumber-pretty", "json:target/cucumber-reports/CucumberTestReport.json",
		"rerun:target/cucumber-reports/re-run.txt" }, plugin = {
				"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html" }, tags = {
						"@Regression" }, monochrome = true

)
public class RunnerTest extends AbstractTestNGCucumberTests {
	final static String author = "Harsh Prasad";
	public static MobileActions actions = null;
	public static String deviceType;

	@Parameters("deviceType")
	@BeforeSuite
	public void setupAppiumEnvironment(String deviceType) {
		RunnerTest.deviceType = deviceType;
		new Report().initiateReport();

		AppiumHandler appiumHandle = new AppiumHandler();

		appiumHandle.setAppiumConfig();
		appiumHandle.startAppium(deviceType);
		actions = new MobileActions(new MobileDriver().getDriver(deviceType));

	}

	@Parameters("deviceType")
	@AfterClass
	public static void writeExtentReport(String deviceType) {
//		actions.getDriver().quit();
		try {
			AppiumHandler.stopAppium();
			Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
			Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
			Reporter.setSystemInfo("Machine", System.getProperty("os.name"));
			Reporter.setSystemInfo("Device", deviceType);
			Reporter.assignAuthor(author);
			Reporter.loadXMLConfig(new File("src/main/resources/extent-config.xml"));
			Thread.sleep(3000);
			File sourceFile = new File(System.getProperty("user.dir") + "/target/cucumber-reports/report.html");
			File destinationFile = new File(Report.reportFolderLoc + "/" + Report.runId + "/report.html");

			FileUtils.copyFile(sourceFile, destinationFile);
			System.out.println("Report location:" + destinationFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
