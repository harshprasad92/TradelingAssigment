Feature: Automation assignment test scenarios

  Background: 
    Given i am on Amazon.com store app home page

  
  Scenario: Check the total displayed number of results for category Smart Home | Televisions
    And Click on Shop by Department from burger menu
    And Choose category "Electronics"
    When Choose sub-category category "TV & VIDEO"
    And Filter with ’Smart TV’ and click show results
    Then Check the total number of results match the total displayed in filter

  
  Scenario: Check the selected currency displayed for the products price
    When User changes currency to AED from USD from currency setting
    Then Selected currency should be displayed for Deals and Promotions products

  @Regression
  Scenario: Check filter by department in Deals and Promotions page
    When select department filter from today deal
    And Select "Automotives" department
    Then The department selected should be filtered

  Scenario: Check Product Detail Page image swipe, Payments Option, Pricing, Stock, Add to Cart
    When User search for a product "Apple" and select product
    Then Usee should be able to swipe the pictures from left to right
    And The price of product should be displayed
    And The payment options should be displayed
    And Stock info should be displayed
    And Product should be added to cart
