Feature: Login Data Driven

  Scenario Outline: Login Data Driven
    Given user launch browser
    And opens URL "http://localhost/opencart/upload/"
    When user navigate to MyAccount menu
    And click on Login
    And User enters email as "<email>" and Password as "<password>"
    And click on Login button
    Then User navigates to MyAccount Page

    Examples:
      | email           | password |
      | aqwea@gmail.com | asdq2we123@ |
      | randomai1l@gmail.com | asdqwe123@ |