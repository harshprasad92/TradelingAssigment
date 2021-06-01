package com.mobile.drivers;

import java.io.File;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.utilities.PropertyFileHandler;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class AndroidDriver {

	private static String deviceName;
	private static String deviceId;
	private static String platformVersion;
	private static String appPackage;
	private static String appActivity;
	private static String appBuildPath;
	private static String appBuildName;
	private static boolean isAppInstalled;
	boolean isMobileConfigSet = false;

	PropertyFileHandler propFileHandle = new PropertyFileHandler();

	// Use this method to initialize all the configurations for android app
	// initialization
	public void setMobileConfig() {
		if (isMobileConfigSet == false) {
			deviceName = propFileHandle.readProperty("mobileConfig", "deviceName");
			deviceId = propFileHandle.readProperty("mobileConfig", "deviceId");
			platformVersion = propFileHandle.readProperty("mobileConfig", "platformVersion");
			appPackage = propFileHandle.readProperty("mobileConfig", "appPackage");
			appActivity = propFileHandle.readProperty("mobileConfig", "appActivity");
			appBuildPath = propFileHandle.readProperty("mobileConfig", "appBuildPath");
			appBuildName = propFileHandle.readProperty("mobileConfig", "appBuildName");
			isAppInstalled = Boolean.parseBoolean(propFileHandle.readProperty("mobileConfig", "isAppInstalled"));
			isMobileConfigSet = true;
		}
	}

	// Use this method to trigger the android app on the device(Real or Emulator)
	public AppiumDriver<MobileElement> createDriver(String deviceType) {
		AppiumDriver<MobileElement> driver = null;
		try {

			setMobileConfig();
			DesiredCapabilities caps = new DesiredCapabilities();
			// condition to handle if for launching directly the app or using apk
			if (isAppInstalled == false) {
				File appdir = new File(appBuildPath);
				File app = new File(appdir, appBuildName);
				caps.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
			}

			caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			caps.setCapability(MobileCapabilityType.UDID, deviceId);
			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, deviceType);
			caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);

			if (!appActivity.isEmpty())
				caps.setCapability("appActivity", appActivity);
			if (!appPackage.isEmpty())
				caps.setCapability("appPackage", appPackage);

			caps.setCapability(MobileCapabilityType.NO_RESET, "true");

			driver = new io.appium.java_client.android.AndroidDriver<MobileElement>(new URL(
					"http://" + AppiumHandler.appiumServerIp + ":" + AppiumHandler.appiumServerPort + "/wd/hub"), caps);

		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return driver;
	}

	public static void removeApp(AppiumDriver<MobileElement> driver) {
		driver.removeApp(appPackage);
	}

}
