package com.amazon.mobilePageObjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cucumber.listener.Reporter;
import com.mobile.drivers.MobileActions;

public class ProductDetailScreen {

	MobileActions actions;

	public ProductDetailScreen(MobileActions actions) {
		this.actions = actions;
		PageFactory.initElements(actions.getDriver(), this);
	}

	@FindBy(xpath = "//android.view.View[@resource-id='anonCarousel1']/android.widget.ListView/android.view.View[1]/android.view.View/android.view.View")
	WebElement productPicture;

	@FindBy(xpath = "//android.view.View[@resource-id='newPitchPriceWrapper_feature_div']/android.view.View/android.view.View")
	List<WebElement> price;

	@FindBy(xpath = "//android.view.View[@resource-id='availability']/android.view.View")
	WebElement availabiilty;

	@FindBy(xpath = "//android.widget.Button[@text='Add to Cart']")
	WebElement addToCartButton;

	@FindBy(xpath = "//android.widget.TextView[@resource-id='com.amazon.mShop.android.shopping:id/chrome_action_bar_cart_count']")
	WebElement cartCount;

	@FindBy(xpath = "//android.view.View[@resource-id='newOfferShippingMessage_feature_div']/android.view.View[2]/android.view.View[2]")
	WebElement paymentDetails;

	@FindBy(xpath = "//android.view.View[@text='Total']/following-sibling::android.view.View")
	WebElement totalAmount;

	public void selectImageAndSwipe() {
		try {
			actions.waitUntilElementIsVisible(productPicture, MobileActions.timeout);
			actions.click(productPicture);
			actions.waitFor(2000);
			actions.swipeLeft();
			actions.getDriver().navigate().back();
			actions.waitFor(2000);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public boolean verifyPrice() {
		boolean flag = false;
		String productPrice = "";
		try {
			actions.scroll(1156, 2157, 1156, 1057);
			for (WebElement ele : price) {

				productPrice = productPrice + actions.getText(ele);
			}
			System.out.println(productPrice);

			if (productPrice.isEmpty() == false) {
				flag = true;
				Reporter.addStepLog("The price of the product is displayed and is " + productPrice);
			} else {
				Reporter.addStepLog("Price of the product is not displayed");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addStepLog("Exception ocurred while verifying Price of the product");
		}
		return flag;
	}

	public boolean verifyStock() {
		boolean flag = false;
		try {
			String stockInfo = actions.getText(availabiilty);
			if (stockInfo.isEmpty() == false) {
				flag = true;
				Reporter.addStepLog("The stock info is available and is : " + stockInfo);
			} else {
				Reporter.addStepLog("The stock info is not available");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addStepLog("Exception ocurred while verifying the stock info.");
		}
		return flag;
	}

	public boolean verifyPaymentAmount() {
		boolean flag = false;
		try {
			actions.click(paymentDetails);
			if (actions.waitUntilElementIsVisible(totalAmount, MobileActions.timeout)) {
				String amount = actions.getText(totalAmount);
				if (amount.isEmpty() == false) {
					flag = true;
					Reporter.addStepLog("The payment amount verified and is " + amount);
				} else {
					Reporter.addStepLog("The payment amount not verified");
				}
			} else {
				Reporter.addStepLog("Payment amount not present");

			}

		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addStepLog("Exception ocurred while verfiying payment amount");
		}
		actions.getDriver().navigate().back();
		return flag;
	}

	public boolean addToCart() {
		boolean flag = false;
		try {
			// if any item is already in cart, this method verifies the new addition be
			// giving the difference between cart old and new value
			int cartQuantityBefore = 0;
			if (actions.waitUntilElementIsVisible(cartCount, MobileActions.timeout)) {
				cartQuantityBefore = Integer.parseInt(actions.getText(cartCount));
			}
			actions.click(addToCartButton);
			actions.waitFor(2000);
			int cartQuantityAfter = Integer.parseInt(actions.getText(cartCount));

			if ((cartQuantityAfter - cartQuantityBefore) == 1) {
				flag = true;
				Reporter.addStepLog("Product successfuly added to cart");

			} else {
				Reporter.addStepLog("Product was not added to cart");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addStepLog("Exception while adding product to cart");
		}
		return flag;
	}

}
