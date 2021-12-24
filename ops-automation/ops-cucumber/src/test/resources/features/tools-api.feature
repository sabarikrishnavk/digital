Feature: Validate api results from in ops-tools-api

  Scenario: Validate the version of deployment
    Given I validate the version of api
    When I get a http 200 response
    Then get the version of current deployment
