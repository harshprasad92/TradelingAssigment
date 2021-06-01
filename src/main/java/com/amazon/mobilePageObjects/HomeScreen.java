package com.amazon.mobilePageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cucumber.listener.Reporter;
import com.mobile.drivers.MobileActions;
import com.reporting.reports.Report;

public class HomeScreen {

	MobileActions actions;

	public HomeScreen(MobileActions actions) {
		this.actions = actions;
		PageFactory.initElements(actions.getDriver(), this);
	}

	@FindBy(id = "com.amazon.mShop.android.shopping:id/chrome_action_bar_burger_icon")
	WebElement burgerMenu;

	@FindBy(xpath = "//android.widget.TextView[@content-desc='Settings button. Double tap for links to change country, sign out, and more.']")
	WebElement settings;

	@FindBy(xpath = "//android.widget.TextView[@text='Country & Language']")
	WebElement countryAndLanguage;

	@FindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.RelativeLayout/android.widget.RelativeLayout[2]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ViewAnimator/android.view.ViewGroup/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout/android.webkit.WebView/android.webkit.WebView/android.view.View[1]/android.view.View/android.view.View/android.view.View[11]")
	WebElement landingCountry;

	@FindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.RelativeLayout/android.widget.RelativeLayout[2]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ViewAnimator/android.view.ViewGroup/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout/android.webkit.WebView/android.webkit.WebView/android.view.View[1]/android.view.View[2]/android.view.View[7]/android.widget.RadioButton[9]")
	WebElement uaeCountry;

	@FindBy(xpath = "//android.widget.Button[@text='Done']")
	WebElement doneButton;

	@FindBy(xpath = "//android.widget.TextView[@text='Shop by Department']")
	WebElement shopByDepartment;

	@FindBy(xpath = "//android.widget.TextView[@text='Electronics']")
	WebElement linkElectronics;

	@FindBy(xpath = "//android.view.View[@text='Electronics']")
	WebElement headerElectronics;

	@FindBy(xpath = "(//android.view.View[@text='Click here to visit icon landing page'])[2]")
	WebElement linkTodaysDeal1;

	@FindBy(xpath = "(//android.view.View[@text='Click here to visit icon landing page'])[3]")
	WebElement linkTodaysDeal2;

	@FindBy(xpath = "//android.view.View[@text='Departments filter']")
	WebElement departmentFilter;

	@FindBy(xpath = "//android.view.View[@text='New deals. Every day. Shop our Deal of the Day, Lightning Deals and more daily deals and limited-time sales.']")
	WebElement dealLabel;

	@FindBy(id = "com.amazon.mShop.android.shopping:id/rs_search_src_text")
	WebElement searchTxtBox;

	@FindBy(xpath = "//android.view.View[@text='RESULTS']/following-sibling::android.view.View[2]")
	WebElement searchedElement;

	public boolean navigateToShopByDepartment() {
		boolean flag = false;
		try {
			actions.click(burgerMenu);
			if (actions.waitUntilElementIsVisible(shopByDepartment, MobileActions.timeout)) {
				actions.click(shopByDepartment);
				flag = true;
				Reporter.addStepLog("Successfuly navigated to Shop By Department");
			} else {
				Reporter.addStepLog("Shop by Department link is not available");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addStepLog("Exception ocurred while navigating to Shop by department");
		}
		return flag;
	}

	public boolean navigateToElectronicsScreen(String storeType) {
		boolean flag = false;
		try {

			actions.click(
					actions.getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + storeType + "']")));
			if (actions.waitUntilElementIsVisible(headerElectronics, MobileActions.timeout)) {
				flag = true;
				Reporter.addStepLog("Successfuly landed in the " + storeType + " department screen");
			} else {
				Reporter.addStepLog(storeType + " section screen did not load");
			}
		} catch (Exception e) {
			Reporter.addStepLog("Exception ocurred while loading " + storeType + " screen");
		}
		return flag;
	}

	public boolean naviateToTodaysDeal() {
		boolean flag = false;
		try {

			// deal icon is randomly displayed at position 2 and 3. Given method handles
			// that scenario
			actions.click(linkTodaysDeal1);
			if (!actions.waitUntilElementIsVisible(dealLabel, MobileActions.timeout)) {
				actions.getDriver().navigate().back();
				actions.click(linkTodaysDeal2);
			}
			if (actions.waitUntilElementIsVisible(departmentFilter, MobileActions.timeout)) {

				flag = true;
			} else {
				Reporter.addStepLog("Department Filter is not available");
			}

		} catch (Exception e) {
			Reporter.addStepLog("Exception in navigating to navigate to today's deal");
		}
		return flag;
	}

	public void selectCurrency(String currency) {
		try {
			actions.click(burgerMenu);
			actions.click(settings);
			actions.click(countryAndLanguage);
			actions.waitUntilElementIsVisible(landingCountry, MobileActions.timeout);
			actions.waitFor(2000);
			actions.click(landingCountry);
			selectCountry(currency);
			actions.click(doneButton);

		} catch (Exception e) {
			Reporter.addStepLog("Exception ocuured while changing currency");
		}
	}

	private void selectCountry(String currency) {
		switch (currency) {
		case "AED":
			actions.scroll(270, 1500, 270, 1000);
			actions.click(uaeCountry);
			// Getting screenshot for country selection
			Report.getScreenshot();
			break;

		default:
			break;
		}
	}

	public boolean verifyCurrencyDisplayed(String currency) {
		boolean flag = false;
		try {
			if (actions.getDriver().getPageSource().contains(currency)) {
				flag = true;
				Reporter.addStepLog("Currency was changed to " + currency);
			} else {
				Reporter.addStepLog("Currency was not changed to " + currency);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addStepLog("Exception ocurred while verifying the currency");
		}

		return flag;
	}

	public void searchAndSelect(String product) {
		try {
			actions.enterText(searchTxtBox, product);
			actions.pressEnter();
			actions.waitUntilElementIsVisible(searchedElement, MobileActions.timeout);
			actions.click(searchedElement);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
