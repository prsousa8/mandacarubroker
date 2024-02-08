Feature: update stocks using PUT API
  Scenario Outline: validate put stock api status code works correctly
    Given I hit the url of put stocks api endpoint
    When I pass the url in the request with <StockID>
    Then I receive the response code as 200
    Examples:
      | StockID    |
      | hdghsdghsd |
