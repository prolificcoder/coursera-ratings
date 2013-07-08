Feature: As a coursera user/enthusiast, I'd
  like to look at all the categories of courses in coursera

  Background: App is loaded
    Given That I open coursera application on my phone
      And I click on the category tab

  Scenario: All the categories on coursera are listed
    Then I should see all the categories that are available on coursera

  Scenario: Should render category courses on clicking a category
    Given I click on Law category
    Then I should see courses under Law category
