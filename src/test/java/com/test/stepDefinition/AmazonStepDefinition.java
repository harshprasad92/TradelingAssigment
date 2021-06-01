package com.test.stepDefinition;

import org.testng.Assert;

import com.amazon.mobilePageObjects.ElectronicsScreen;
import com.amazon.mobilePageObjects.HomeScreen;
import com.amazon.mobilePageObjects.LandingScreen;
import com.amazon.mobilePageObjects.ProductDetailScreen;
import com.amazon.mobilePageObjects.TodayDeal;
import com.mobile.drivers.RunnerTest;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AmazonStepDefinition extends RunnerTest {

	LandingScreen landingScreen;
	HomeScreen homeScreen;
	ElectronicsScreen electronicScreen;
	TodayDeal todayDealScreen;
	ProductDetailScreen productScreen;

	int filterCount = 0;
	String department = "";

	@Before
	public void initiateMobileApp() {

		actions.getDriver().launchApp();
	}

	@After
	public void quitApp() {
		actions.getDriver().terminateApp("com.amazon.mShop.android.shopping");
	}

	// Step definition for background steps
	@Given("^i am on Amazon\\.com store app home page$")
	public void i_am_on_Amazon_com_store_app_home_page() throws Throwable {
		landingScreen = new LandingScreen(actions);
		landingScreen.skipLogin();
	}

	/**
	 * Step definition for scenario 'Check the total displayed number of results for
	 * category Smart Home | Televisions'
	 * 
	 * @throws Throwable
	 */
	@Given("^Click on Shop by Department from burger menu$")
	public void click_on_Shop_by_Department_from_burger_menu() throws Throwable {
		homeScreen = new HomeScreen(actions);
		Assert.assertTrue(homeScreen.navigateToShopByDepartment());
	}

	@Given("^Choose category \"([^\"]*)\"$")
	public void choose_category_Electronics(String storeType) throws Throwable {
		Assert.assertTrue(homeScreen.navigateToElectronicsScreen(storeType));
	}

	@When("^Choose sub-category category \"([^\"]*)\"$")
	public void choose_sub_category_category(String category) throws Throwable {
		electronicScreen = new ElectronicsScreen(actions);
		Assert.assertTrue(electronicScreen.selectCatrgory(category));
	}

	@When("^Filter with ’Smart TV’ and click show results$")
	public void filter_with_Smart_TV_and_click_show_results() throws Throwable {
		filterCount = electronicScreen.applyFilter();
	}

	@Then("^Check the total number of results match the total displayed in filter$")
	public void check_the_total_number_of_results_match_the_total_displayed_in_filter() throws Throwable {
		Assert.assertTrue(electronicScreen.verifyNumberOfRecordsDisplayed(filterCount));
	}

	/**
	 * Step Definition for scenario 'Check filter by department in Deals and
	 * Promotions page'
	 * 
	 * @throws Throwable
	 */
	@When("^select department filter from today deal$")
	public void select_department_filter_from_today_deal() throws Throwable {
		homeScreen = new HomeScreen(actions);
		Assert.assertTrue(homeScreen.naviateToTodaysDeal());

	}

	@When("^Select \"([^\"]*)\" department$")
	public void select_software_department(String department) throws Throwable {
		this.department = department;
		todayDealScreen = new TodayDeal(actions);
		todayDealScreen.selectDepartment(department);
	}

	@Then("^The department selected should be filtered$")
	public void the_department_selected_should_be_filtered() throws Throwable {
		Assert.assertTrue(todayDealScreen.verifyDepartmentSelected(department));
	}

	/**
	 * Step Definition for scenario 'Check the selected currency displayed for the
	 * products price'
	 * 
	 * @throws Throwable
	 */
	@When("^User changes currency to AED from USD from currency setting$")
	public void user_changes_currency_to_AED_from_USD_from_currency_setting() throws Throwable {
		homeScreen = new HomeScreen(actions);
		homeScreen.selectCurrency("AED");
	}

	@Then("^Selected currency should be displayed for Deals and Promotions products$")
	public void selected_currency_should_be_displayed_for_Deals_and_Promotions_products() throws Throwable {
		homeScreen.naviateToTodaysDeal();
		Assert.assertTrue(homeScreen.verifyCurrencyDisplayed("AED"));
	}

	/**
	 * Step Definition for scenario 'Check Product Detail Page image swipe, Payments
	 * Option, Pricing, Stock, Add to Cart'
	 * 
	 * @throws Throwable
	 */

	@When("^User search for a product \"([^\"]*)\" and select product$")
	public void user_search_for_a_product_Apple_and_select_product(String product) throws Throwable {
		homeScreen = new HomeScreen(actions);
		homeScreen.searchAndSelect(product);

	}

	@Then("^Usee should be able to swipe the pictures from left to right$")
	public void usee_should_be_able_to_swipe_the_pictures_from_left_to_right() throws Throwable {
		productScreen = new ProductDetailScreen(actions);
		productScreen.selectImageAndSwipe();
	}

	@Then("^The price of product should be displayed$")
	public void the_price_of_product_should_be_displayed() throws Throwable {
		Assert.assertTrue(productScreen.verifyPrice());
	}

	@Then("^The payment options should be displayed$")
	public void the_payment_options_should_be_displayed() throws Throwable {
		Assert.assertTrue(productScreen.verifyPaymentAmount());
	}

	@Then("^Stock info should be displayed$")
	public void stock_info_should_be_displayed() throws Throwable {
		Assert.assertTrue(productScreen.verifyStock());
	}

	@Then("^Product should be added to cart$")
	public void product_should_be_added_to_cart() throws Throwable {
		Assert.assertTrue(productScreen.addToCart());
	}
}
