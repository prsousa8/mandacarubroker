Feature: Get all stocks
    Scenario: Verify the get api for the stocks
      Given I hit the url of get stocks api endpoint
      When I pass the url in the request
      Then I receive the response code as 200

  Scenario Outline: Verify the stock values are corrects
    Given I hit the url of get stocks api endpoint
    When I pass the url <IdTest> in the request
    And Compare to <symbol> e <companyName> e <price>
    Then I receive the response code as 200
    Examples:
      | IdTest | symbol | companyName | price |
      | ID2    | LL2    | Company LL  | 95.25 |

  Scenario: Verify the id of the first stock is correct
      Given I hit the url of get stocks api endpoint
      When I pass the url in the request
      Then I verify that the id of the first product is 36 letters

