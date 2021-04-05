Feature: Promotions feature


  Scenario: To validate promotions endpoint with valid apiKey request
    Given I want to execute "popcorn" api
    When I submit GET request with endPoint as "promotion" with valid apiKey
    Then I should get "200" as StatusCode in response
    Then I should get response within "5000" milliseconds


  Scenario: To validate promotions endpoint with invalid apiKey request
    Given I want to execute "popcorn" api
    When I submit GET request with endPoint as "promotion" with invalid apiKey as "invalidApikey"
    Then I should get "403" as StatusCode in response
    Then I should get response within "5000" milliseconds
    Then I should validate response for the key "$.error.message" should have value as "invalid api key"
    Then I should validate response for the key "$.error.code" should have value as "8001"
    Then I should validate response for the key "$.error.requestId" should not be null


  Scenario: To validate json schema for promotions endpoint with valid apiKey request
    Given I want to execute "popcorn" api
    When I submit GET request with endPoint as "promotion" with valid apiKey
    Then I should get "200" as StatusCode in response
    Then I should get response within "5000" milliseconds
    Then I should validate json schema in "promotionSchema.json" the response


  Scenario: To validate promotions endpoint with corresponding data type
    Given I want to execute "popcorn" api
    When I submit GET request with endPoint as "promotion" with valid apiKey
    Then I should get "200" as StatusCode in response
    Then I should get response within "5000" milliseconds
    Then I should validate response for the key "$.promotions[*].promotionId" should be of type "string"


  Scenario: To validate promotions endpoint with multiple valid values
    Given I want to execute "popcorn" api
    When I submit GET request with endPoint as "promotion" with valid apiKey
    Then I should get "200" as StatusCode in response
    Then I should get response within "5000" milliseconds
    Then I should validate response for the key "$.promotions[*].promoType" should have value as "EPISODE|MOVIE|SERIES|SEASON"