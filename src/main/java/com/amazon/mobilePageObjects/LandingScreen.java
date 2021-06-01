package com.amazon.mobilePageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cucumber.listener.Reporter;
import com.mobile.drivers.MobileActions;

public class LandingScreen {

	MobileActions actions;

	public LandingScreen(MobileActions actions) {
		this.actions = actions;
		PageFactory.initElements(actions.getDriver(), this);
	}

	@FindBy(id = "com.amazon.mShop.android.shopping:id/sso_splash_logo")
	WebElement amazonLogo;

	@FindBy(id = "com.amazon.mShop.android.shopping:id/skip_sign_in_button")
	WebElement skipSignIn;

	// This method handles the intermittent landing on the signup screen
	public void skipLogin() {

		try {
			if (actions.waitUntilElementIsVisible(amazonLogo, MobileActions.timeout)) {
				actions.click(skipSignIn);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addStepLog("Exception ocurred while performing skip login");
		}

	}

}
