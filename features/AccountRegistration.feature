Feature: Account Registration
@regression
  Scenario: Successful Account Registrations with Valid user details
    Given User Launch browser
    Given opens URL "http://demo.opencart.com/"
    When User navigate to MyAccount menu
    And Click on Register
    Then User navigate to Register Account page
    When user provide valid details
    And Click on Continue
    Then User should see "Your Account Has Been Created!" message
