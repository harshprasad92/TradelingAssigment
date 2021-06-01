package com.mobile.drivers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class MobileDriver {

	public AppiumDriver<MobileElement> getDriver(String platform) {

		AppiumDriver<MobileElement> driver = null;

		switch (platform.toLowerCase()) {

		case "android":
			driver = new AndroidDriver().createDriver(platform);
			break;

		case "ios":
			//to be implemented for ios app
			break;
		}

		return driver;

	}

}
