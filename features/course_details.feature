Feature: As a coursera user I'd like to see see more details
  about a course that I am interested in

  Background: Course details page
    Given That I am on Startup Engineering course details view

  Scenario: Should have information related to the course
    Then I should see url, description, university name, category and ratings of Startup Engineering course
      And I should see upvote and downvote

  Scenario: An un-authenticated user should not be able to rate
    Given That I am not logged in
      And I click on upvote
    Then I should see login view
      And Vote for the course should not be counted






