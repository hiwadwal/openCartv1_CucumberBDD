Feature: loginDDT
#   we use background when we have commons steps for begging of the feature file
#   we cant take feature file steps from middle or at the end of feature file steps and once we move those common steps from multiple feature
#  to backgourd then remove those steps from feature outline the usage of background to avoid dublicate the features code and
#  when we execute or run the test the Background steps will run first

Background:
  Given User Launch browser
  And opens URL "https://demo.opencart.com/"
  When User navigate to MyAccount menu
  And click on Login
@sanaty @regression
  Scenario Outline: Login Data Driven
    And User enters Email as "<email>" and Password as "<password>"
    And Click on Login button
    Then User navigates to MyAccount Page

    Examples:
      | email                     | password  |
      | mujeeb.hiwadwal@gmail.com | 2250mh123 |
      | ahmad.belal@gmail.com     | 2250mh123 |
      | mujeeb.hiwadwal@gmail.com | 2342mh321 |

  Scenario Outline: login Data Driven2


    Then Check User navigates to MyAccount Page by passing Email and Password with excel row "<row_index>"
    Examples:
      | row_index |
      | 1         |
      | 2         |
      | 3         |
