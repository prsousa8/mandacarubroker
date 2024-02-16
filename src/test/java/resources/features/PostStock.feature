Feature: insert stocks using POST API
  Scenario Outline: validate post stock api status code works correctly
    Given I hit the url of post stocks api endpoint
    When I pass the url in the request
    And I pass the request body of stock id <IdTest>
    Then I receive the response code as 200
    Examples:
      | IdTest       |
      | IdTestString |
