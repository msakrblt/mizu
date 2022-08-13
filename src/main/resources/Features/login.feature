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

    @musa
    Scenario: Login With Google
      When I see login page
      When I see signIn text element in 20 seconds
      Then I see login with google element
      When I click login with google element
      And I wait to 3 seconds
      Then I open to signIn google application
      And I wait to 5 seconds
      Then I see signIn google page
      Then I see email element
      And I click email element
      When I fill:
        | email text | musa.krblt.23@gmail.com |
      And I click next button element


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