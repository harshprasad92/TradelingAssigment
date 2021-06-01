package com.amazon.mobilePageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cucumber.listener.Reporter;
import com.mobile.drivers.MobileActions;
import com.reporting.reports.Report;

public class ElectronicsScreen {

	MobileActions actions;

	public ElectronicsScreen(MobileActions actions) {
		this.actions = actions;
		PageFactory.initElements(actions.getDriver(), this);
	}

	@FindBy(xpath = "//android.view.View[@text='RESULTS']")
	WebElement labelResults;

	@FindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.RelativeLayout/android.widget.RelativeLayout[2]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ViewAnimator/android.view.ViewGroup/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[1]/android.view.View/android.widget.ToggleButton")
	WebElement filters;

	@FindBy(xpath = "//android.view.View[@text='Smart TV']")
	WebElement iconSmartTv;

	@FindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.RelativeLayout/android.widget.RelativeLayout[2]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ViewAnimator/android.view.ViewGroup/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout/android.webkit.WebView/android.webkit.WebView/android.view.View[4]/android.view.View/android.view.View/android.view.View[2]/android.view.View")
	WebElement resultButton;

	@FindBy(xpath = "//android.view.View[@text='RESULTS Price and other details may vary based on size and color']")
	WebElement labelResult2;

	@FindBy(xpath = "//android.view.View[@text='product-detail']")
	List<WebElement> productList;

	@FindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.RelativeLayout/android.widget.RelativeLayout[2]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ViewAnimator/android.view.ViewGroup/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout/android.webkit.WebView/android.webkit.WebView/android.view.View[1]/android.view.View/android.view.View[45]/android.widget.ListView/android.view.View[3]/android.view.View")
	WebElement buttonNext;

	String homeAudioLink = "//android.view.View[@text='HOME AUDIO']";
	String cameraPhotoLink = "//android.view.View[@text='CAMERA & PHOTO']";

	// This method can be used to elect any category
	public boolean selectCatrgory(String category) {

		boolean flag = false;
		try {

			actions.swipeUp(actions.getWebElementWithXpath(homeAudioLink),
					actions.getWebElementWithXpath(cameraPhotoLink));
			// dynamic xpath handle
			actions.click(actions.getDriver().findElement(By.xpath("//android.view.View[@text='" + category + "']")));
			if (actions.waitUntilElementIsVisible(labelResults, MobileActions.timeout)) {
				flag = true;
				Reporter.addStepLog("Successfully selected category " + category);
			} else {
				Reporter.addStepLog("Records for the category " + category + " are not loaded");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addStepLog("Exception ocurred while selecting category " + category);
		}
		return flag;
	}

	public int applyFilter() {
		int countAfterFilter = 0;
		try {
			actions.click(filters);
			actions.scroll(270, 1700, 270, 140);
			actions.click(iconSmartTv);

			actions.click(filters);
			actions.waitFor(3000);
			String resultString = actions.getText(resultButton);
			countAfterFilter = Integer.parseInt(resultString.split(" ")[1]);
			actions.click(resultButton);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return countAfterFilter;
	}

	public boolean verifyNumberOfRecordsDisplayed(int record) {
		boolean bflag = false;

		int count = 0;
		try {

			// to check if the page is loaded with list of products
			while ((actions.waitUntilElementIsVisible(labelResults, MobileActions.timeout)
					|| actions.waitUntilElementIsVisible(labelResult2, MobileActions.timeout))) {

				// to verify if the total count of products has gone above total number of
				// results
				if (count > record) {

					Reporter.addStepLog("The number of records displayed are greater than the number of result");
					break;
				}
				actions.scrollToBottom();
				count = count + productList.size();

				// to check if next button is disabled i.e. no more products will be listed
				if (actions.waitUntilElementToBeClickable(buttonNext)) {
					actions.click(buttonNext);

				} else {

					break;
				}
			}

			// to verify if the number of product is equal to result count
			if (count == record) {
				bflag = true;
			} else {
				Reporter.addStepLog(
						"The number of record displayed are " + count + ", the number shown in result " + record);
				Report.getScreenshot();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bflag;
	}

}
