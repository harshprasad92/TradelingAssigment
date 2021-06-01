package com.mobile.drivers;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cucumber.listener.Reporter;
import com.google.common.base.Function;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

public class MobileActions {

	public static AppiumDriver<MobileElement> driver;
	Duration timeOutDuration = Duration.of(2, ChronoUnit.SECONDS);
	Duration pollingDuration = Duration.of(250, ChronoUnit.MILLIS);
	public static long timeout = 120;

	public MobileActions(AppiumDriver<MobileElement> driver) {
		MobileActions.driver = driver;
	}

	public AppiumDriver<MobileElement> getDriver() {

		return driver;
	}

	public void waitFor(long milliSec) throws InterruptedException {
		Thread.sleep(milliSec);
	}

	public boolean click(WebElement element) {
		boolean bFlag = false;
		try {
			WebElement ele = verifyElementClickable(element);
			if (ele != null) {
				ele.click();
				bFlag = true;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bFlag;
	}

	public String getText(WebElement element) {
		String text = null;
		try {
			if (visibilityofWebelement(element))
				text = element.getText();

		} catch (TimeoutException t) {
			t.printStackTrace();
		}

		return text;
	}

	public void enterText(WebElement element, String textToEnter) {
		try {

			if (visibilityofWebelement(element)) {
				element.clear();
				element.sendKeys(textToEnter);
			}

		} catch (TimeoutException t) {

		} catch (Exception e) {
		}

	}

	private WebElement verifyElementClickable(WebElement element) {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 40);
			return wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (Exception e) {

			throw e;
		}

	}

	public boolean waitUntilElementIsVisible(WebElement element, long timeOutInSeconds) {
		boolean bFlag = false;
		try {
			if (existenceofWebelement(element, timeOutInSeconds)) {
				{
					bFlag = true;
				}

			}

		} catch (TimeoutException t) {

			Reporter.addStepLog("Timeout exception in finding element");
		} catch (Exception e) {
		}
		return bFlag;

	}

	public WebElement getWebElementWithXpath(String property) {
		return getDriver().findElement(By.xpath(property));
	}

	public void swipeUp(WebElement fromPosition, WebElement toPosition) {
		Dimension size = fromPosition.getSize();
		Dimension size1 = toPosition.getSize();
		TouchAction swipe = new TouchAction(getDriver())
				.press(ElementOption.element(fromPosition, size.width / 2, size.height - 20))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(5)))
				.moveTo(ElementOption.element(toPosition, size1.width / 2, size1.height / 2 + 30)).release();
		swipe.perform();

	}

	public void scroll(int fromXCordinate, int fromYCordinate, int toXCordinate, int toYCordinate) {
		try {
			TouchAction action = new TouchAction(driver);
			action.longPress(PointOption.point(fromXCordinate, fromYCordinate))
					.moveTo(PointOption.point(toXCordinate, toYCordinate)).release().perform();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void swipeLeft() {
		TouchAction swipe = new TouchAction(getDriver()).press(PointOption.point(1400, 1500))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(5))).moveTo(PointOption.point(200, 1500))
				.release();
		swipe.perform();

	}

	public void pressEnter() {
		// TODO Auto-generated method stub

		try {

			waitFor(2000);
			getDriver().getKeyboard().pressKey(Keys.ENTER);
			waitFor(2000);
		} catch (Exception e) {
		}
	}

	private boolean existenceofWebelement(WebElement element, long timeOutInSeconds) {

		try {

			FluentWait<WebElement> _waitForElement = new FluentWait<WebElement>(element);
			_waitForElement.pollingEvery(pollingDuration);
			Duration timeout = Duration.of(timeOutInSeconds, ChronoUnit.SECONDS);
			_waitForElement.withTimeout(timeout);
			_waitForElement.ignoring(NoSuchElementException.class);
			_waitForElement.ignoring(StaleElementReferenceException.class);
			_waitForElement.ignoring(ElementNotVisibleException.class);

			Function<WebElement, Boolean> elementVisibility = new Function<WebElement, Boolean>() {

				public Boolean apply(WebElement element) {
					// TODO Auto-generated method stub

					return element.isEnabled();
				}

			};

			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOf(element));

			return _waitForElement.until(elementVisibility);

		} catch (Exception e) {

		}

		return false;
	}

	public void scrollToBottom() {
		scroll(270, 2375, 270, 140);
		scroll(270, 2375, 270, 140);
		scroll(270, 2375, 270, 140);
	}

	private boolean visibilityofWebelement(WebElement element) {

		try {
			FluentWait<WebElement> _waitForElement = new FluentWait<WebElement>(element);
			_waitForElement.pollingEvery(pollingDuration);
			_waitForElement.withTimeout(timeOutDuration);
			_waitForElement.ignoring(NoSuchElementException.class);
			_waitForElement.ignoring(StaleElementReferenceException.class);
			_waitForElement.ignoring(ElementNotVisibleException.class);

			Function<WebElement, Boolean> elementVisibility = new Function<WebElement, Boolean>() {

				public Boolean apply(WebElement element) {
					// TODO Auto-generated method stub

					return element.isDisplayed();
				}

			};

			return _waitForElement.until(elementVisibility);

		} catch (Exception e) {

		}

		return false;
	}

	public boolean waitUntilElementToBeClickable(WebElement element) {
		boolean bFlag = false;
		try {

			WebElement ele = verifyElementClickable(element);
			if (ele != null) {
				bFlag = true;

			}
		}

		catch (TimeoutException t) {

		} catch (Exception e) {
		}
		return bFlag;
	}

	public boolean isChecked(WebElement element) {
		boolean bFlag = false;
		try {
			if (visibilityofWebelement(element)) {
				String flag = "";
				flag = element.getAttribute("checked");
				bFlag = Boolean.parseBoolean(flag);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bFlag;
	}

	public boolean tap(WebElement element) {
		boolean bFlag = false;
		try {
			WebElement ele = verifyElementClickable(element);
			if (ele != null) {

				TouchAction action = new TouchAction(driver);
				action.tap(new TapOptions().withElement(new ElementOption().withElement(ele))).perform();
				bFlag = true;

			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return bFlag;
	}
}
