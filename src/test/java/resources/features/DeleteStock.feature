Feature: delete stocks using DELETE API
  Scenario Outline: validate delete stock api status code works correctly
    Given I hit the url of delete stocks api endpoint
    When I pass the url in the request delete with <StockID>
    Then I receive the response code as 200
    Examples:
      | StockID      |
      | hdghsdghsd |
