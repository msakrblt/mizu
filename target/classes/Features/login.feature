Feature: Mizu Sign-In

  Background:
    Given I Open To Chrome Browser
    And I open to mizu application
    And screen to maximize

  @musa
  Scenario: Failed Sign In
    When I see login page
    When I see signIn text element in 5 seconds
    Then I see username textbox element
    Then I see password textbox element
    And I am registered with user1
    When I fill:
      | username textbox | my username |
      | password textbox | try123 |
    And I click login button element
    Then I see failed message element
    Then I wait to 5 seconds

  @musa
  Scenario: Success Sign In
    When I see login page
    When I see signIn text element in 20 seconds
    Then I see username textbox element
    Then I see password textbox element
    And I am registered with user1
    When I fill:
      | username textbox | my username |
      | password textbox | my password |
    And I click login button element
    Then I wait to 5 seconds
    Then I see home page
    Then I see my account element in 20 seconds

  @musa2
  Scenario: Check Forgot Password
    When I see login page
    When I see signIn text element in 5 seconds
    Then I see username textbox element
    Then I see password textbox element
    And I am registered with user1
    When I fill:
      | username textbox | my username |
      | password textbox | try123 |
    And I click login button element
    Then I see failed message element
    Then I wait to 2 seconds
    Then I see failed message button element
    When I click failed message button element
    Then I see forgot password element
    When I click forgot password element
    Then I see email textbox element
    When I fill:
      | email textbox | forgot@gmail.com |
    Then I see forgot password send button element
    And I wait to 4 seconds
    When I click forgot password send button element
    And I wait to 4 seconds
    Then I see forgot password succes message element