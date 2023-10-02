Feature: Login Data Driven with Excel

  Scenario Outline: Login Data Driven Excel
    Given user launch browser
    And opens URL "http://localhost/opencart/upload/"
    When user navigate to MyAccount menu
    And click on Login
    Then check User navigates to MyAccount Page by passing Email and Password with excel row "<row_index>"

    Examples:
      |row_index|
      |1|
      |2|
      |3|

