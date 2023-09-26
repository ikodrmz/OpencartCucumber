Feature: Login with Valid Credentials

@sanity
  Scenario: Successful Login with Valid Credentials
    Given user launch browser
    And opens URL "http://localhost/opencart/upload/"
    When user navigate to MyAccount menu
    And click on Login
    And User enters email as "aqwea@gmail.com" and Password as "asdqwe123@"
    And click on Login button
    Then User navigates to MyAccount Page