@smoke
Feature: Submit an intake form with multiple locations

  Scenario: Complete an intake form with two locations and capture the intake form ID
    Given I open the intake form
    And I complete the primary fields with first name "TODO-First", last name "TODO-Last" and request type "TODO-Type"
    When I add a location named "TODO-Location-1" of type "TODO-Type-A" in category "TODO-cat-a"
    And I add a location named "TODO-Location-2" of type "TODO-Type-B" in category "TODO-cat-b"
    And I save the changes
    Then I should see an intake form ID
    When I click done
    # The browser window is closed by the @After hook (driver.quit()).
