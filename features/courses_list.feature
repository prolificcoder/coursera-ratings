Feature: As a coursera user/enthusiast, I'd
  like to look at all the courses, sorted by popularity

  Background: App is loaded
    Given That I open coursera application on my phone

  Scenario: All the courses on coursera are listed
    Then I should see all the courses tha are available on coursera
      And I should also see course rating

  Scenario: Courses are sorted by number of votes
    Then I should see courses sorted by number of votes
      But I should not see courses sorted by rating

  Scenario Outline: Courses can be searched with partial text or any case
    Given I enter <Input Text> in search box
    Then I should see <Course Name> course
    But I should not see <Negative Text> course
  Examples:
    | Input Text       | Course Name         | Negative Text |
    | Public Speaking  | Public Speaking     | Startup Engineering |
    | Engineering      | Startup Engineering | Public Speaking |
    | pubLic speAKing  | Public Speaking     | Startup Engineering |
    | aRTtup           | Startup Engineering | Public Speaking |

  Scenario: Should render course details on clicking a course
    Given I enter Machine Learning in search box
    Given I click on Machine Learning
    Then I should see Machine Learning course details
