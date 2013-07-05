Feature:

  Scenario: An authenticated user should be able to rate
    Given That user is logged in
    And I click on upvote
    Then Vote for the course should be counted
    And Rating of the course should be updated

  Scenario: An authenticated user's rating should be preserved
    Given That I upvote Startup Engineering course
    And That I click back button to navigate to course list
    Then I should see updated rating for Startup Engineering course
    And I click on Startup Engineering course
    Then I should see my upvote for Startup Engineering course

  Scenario: An authenticated user can only one of upvote and downvote
    Given That I click on upvote
    Then Downvote should be disabled
    And I click on downvote
    Then I should get an error message

  Scenario: An authenticated user can cancel his vote
    Given That I click on upvote
    And That I click on upvote
    Then I should see upvote and downvote enabled
    And Vote should get cancelled