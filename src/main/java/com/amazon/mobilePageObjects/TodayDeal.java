package com.amazon.mobilePageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cucumber.listener.Reporter;
import com.mobile.drivers.MobileActions;

public class TodayDeal {

	MobileActions actions;

	public TodayDeal(MobileActions actions) {
		this.actions = actions;
		PageFactory.initElements(actions.getDriver(), this);
	}

	@FindBy(xpath = "//android.widget.Button[@text='Submit']/following-sibling::android.view.View")
	WebElement finishButton;

	@FindBy(xpath = "//android.view.View[@text='Departments filter']")
	WebElement departmentFilter;

	public void selectDepartment(String department) {

		try {
			actions.click(departmentFilter);
			actions.click(actions.getWebElementWithXpath("//android.widget.CheckBox[@text='" + department + "']"));
			actions.click(finishButton);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean verifyDepartmentSelected(String department) {

		boolean flag = false;
		try {
			actions.click(departmentFilter);
			actions.waitUntilElementIsVisible(
					actions.getWebElementWithXpath("//android.widget.CheckBox[@text='" + department + "']"),
					MobileActions.timeout);
			if (actions.isChecked(
					actions.getWebElementWithXpath("//android.widget.CheckBox[@text='" + department + "']"))) {
				flag = true;
				Reporter.addStepLog("The department filter " + department + " is selected");
			} else {
				Reporter.addStepLog("The department filter " + department + " was not selected");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
