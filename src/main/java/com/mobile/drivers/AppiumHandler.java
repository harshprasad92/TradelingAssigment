package com.mobile.drivers;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.common.collect.ImmutableMap;
import com.utilities.PropertyFileHandler;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumHandler {

	AppiumServiceBuilder serviceBuilder;
	public static AppiumDriverLocalService appiumService;

	public static String appiumServerIp;
	public static int appiumServerPort;
	private static String appiumJsPath;
	private static String nodeJsPath;
	private static String androidHome;
	private static int appiumCommandTimeout;
	boolean isAppiumConfigSet = false;;

	PropertyFileHandler propFileHandle = new PropertyFileHandler();

	public void setAppiumConfig() {

		if (isAppiumConfigSet == false) {
			appiumServerIp = propFileHandle.readProperty("mobileConfig", "appiumServerIp");

			appiumServerPort = Integer.parseInt(propFileHandle.readProperty("mobileConfig", "appiumServerPort"));
			appiumJsPath = propFileHandle.readProperty("mobileConfig", "appiumJsPath");
			nodeJsPath = propFileHandle.readProperty("mobileConfig", "nodeJsPath");
			androidHome = propFileHandle.readProperty("mobileConfig", "androidHome");
			appiumCommandTimeout = Integer
					.parseInt(propFileHandle.readProperty("mobileConfig", "appiumCommandTimeout"));
			isAppiumConfigSet = true;
		}
	}

	// Use this method to start up a new Appium session
	public void startAppium(String deviceType) {
		try {
			// Set Capabilities for appium i.e. command timeout
			DesiredCapabilities appiumCapabilities;
			appiumCapabilities = new DesiredCapabilities();
			appiumCapabilities.setCapability("noReset", "false");
			appiumCapabilities.setCapability("newCommandTimeout", appiumCommandTimeout);

			// Build the Appium service
			serviceBuilder = new AppiumServiceBuilder();
			serviceBuilder.withIPAddress(appiumServerIp);
			serviceBuilder.usingPort(appiumServerPort);
			serviceBuilder.usingDriverExecutable(new File(nodeJsPath));
			serviceBuilder.withArgument(GeneralServerFlag.LOG_LEVEL, "info");
			serviceBuilder.withCapabilities(appiumCapabilities);
			serviceBuilder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
			serviceBuilder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
			serviceBuilder.withAppiumJS(new File(appiumJsPath));

			if (deviceType.equalsIgnoreCase("android"))
				serviceBuilder.withEnvironment(ImmutableMap.of("ANDROID_HOME", androidHome));
			// Start the server with the builder
			appiumService = AppiumDriverLocalService.buildService(serviceBuilder);
			appiumService.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Use this method to stop the appium session if it is running
	public static void stopAppium() {
		if (appiumService.isRunning()) {
			appiumService.stop();
			Runtime rt = Runtime.getRuntime();
			try {
				Process proc = rt.exec("ping localhost");
				proc.destroy();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

}
