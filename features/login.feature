Feature: login
@sanaty
  Scenario: Successful Login with Valid Credentials
    Given User Launch browser
    And opens URL "http://demo.opencart.com/"
    When User navigate to MyAccount menu
    And click on Login
    And User enters Email as "mujeeb.hiwadwal@gmail.com" and Password as "2250mh123"
    And Click on Login button
    Then User navigates to MyAccount Page


