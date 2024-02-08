Feature: update stocks using PUT API
  Scenario Outline: validate put stock api status code works correctly
    Given I hit the url of put stocks api endpoint
    When I pass the url in the request with <StockID>
    Then I receive the response code put as 415
    Examples:
      | StockID      |
      | 3fda04c0-26dc-4dc5-ae94-c53c925848ac |
