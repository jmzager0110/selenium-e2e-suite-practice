@smoke
Feature: Submit a proposal end to end

  # The two variants differ only in how they reach application page one:
  #   client: Accounts > first result > Add Locations
  #   agent:  New Application
  # Everything after "I start a new application" is shared.

  Scenario Outline: <role> submits a proposal with two locations and captures the proposal ID
    Given I open the application and log in as a "<role>"
    When I start a new application
    And I complete page one with applicant name "TODO-Applicant", contact email "TODO@example.com" and phone "5555550100"
    And I continue to page two
    And I complete page two with effective date "TODO-01/01/2027", industry "TODO-Industry" and description "TODO-Description"
    And I add the following locations:
      | name            | street       | city       | state | zip   | classification | coverage type | additional options |
      | TODO-Location-1 | TODO-Street1 | TODO-City1 | IL    | 62701 | TODO-Class-A   | TODO-Cov-A    | TODO-Opt-1         |
      | TODO-Location-2 | TODO-Street2 | TODO-City2 | IL    | 60601 | TODO-Class-B   | TODO-Cov-B    | TODO-Opt-2, TODO-Opt-3 |
    And I continue to the selection page
    And I choose the selection "TODO-Selection"
    And I continue to the payment options page
    And I accept the default payment option
    # To pick one explicitly instead:  And I select the payment option "TODO-Payment"
    And I continue to the submit page
    And I submit the proposal
    Then I should see a proposal ID
    # The browser window is closed by the @After hook (driver.quit()).

    @client
    Examples:
      | role   |
      | client |

    @agent
    Examples:
      | role  |
      | agent |
