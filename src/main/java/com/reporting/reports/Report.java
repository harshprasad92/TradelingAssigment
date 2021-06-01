package com.reporting.reports;

import java.io.File;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.cucumber.listener.Reporter;
import com.mobile.drivers.MobileActions;

public class Report {

	public static String reportFolderLoc = "report";
	public static String runId = "";
	static String screenshotLoc = "";

	public void initiateReport() {

		runId = createUniqueId();
		System.out.println("Run id: " + runId);
		createReportDirectory();

	}

	public static String createUniqueId() {
		Random unique = new Random();
		return "" + unique.nextInt(999999);
	}

	public static void main(String[] args) {
		System.out.println(createUniqueId());
	}

	public void createReportDirectory() {

		try {

			File executionFolder = new File(reportFolderLoc + "/" + runId);
			screenshotLoc = reportFolderLoc + "/" + runId + "/screenshots";
			File screenShotFolder = new File(screenshotLoc);
			if (!executionFolder.exists()) {
				if (executionFolder.mkdirs()) {
					if (!screenShotFolder.exists()) {
						screenShotFolder.mkdir();
					}
				} else {
					System.out.println("Failed to create report directory");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// this method is to capture screenshot and log it to report
	public static String getScreenshot() {
		String screenshot = "";
		File sourcePath = null;
		String screenshotId = "";
		try {
			sourcePath = ((TakesScreenshot) MobileActions.driver).getScreenshotAs(OutputType.FILE);
			screenshotId = "screen_" + createUniqueId();
			screenshot = screenshotLoc + "/" + screenshotId + ".png";
			File destinationPath = new File(screenshot);
			FileUtils.copyFile(sourcePath, destinationPath);
			Reporter.addScreenCaptureFromPath(destinationPath.toString());

			screenshot = destinationPath.getAbsolutePath();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return screenshotId;
	}

}
