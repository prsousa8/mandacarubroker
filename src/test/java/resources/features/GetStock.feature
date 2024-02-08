Feature: Get all stocks
  Scenario: Get all stocks
    Given I have more than 3 stocks in my database
    And One of these products is a CellPhone
    Then All Stocks should be returned sucessfully

    Scenario: Verify the get api for the stocks
      Given I hit the url of get stocks api endpoint
      When I pass the url in the request
      Then I receive the response code as 200

    Scenario: Verify the id of the first stock is correct
      Given I hit the url of get stocks api endpoint
      When I pass the url in the request
      Then I verify that the id of the first product is 36 letters

