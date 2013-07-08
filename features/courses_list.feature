Feature: As a coursera user/enthusiast, I'd
  like to look at all the courses, sorted by popularity

  Background: App is loaded
    Given That I open coursera application on my phone

  Scenario: Courses are sorted by number of votes
    Then I should see courses sorted by number of votes
      But I should not see courses sorted by rating

  @passing
  Scenario Outline: Courses can be searched with partial text or any case
    Given I enter <Input Text> in course search box
    Then I should see the <Course Name> course
    But I should not see <Negative Text> course
  Examples:
    | Input Text       | Course Name         | Negative Text |
    | Public Speaking  | Public Speaking     | Startup Engineering |
    | Engineering      | Startup Engineering | Public Speaking |
    | pubLic speAKing  | Public Speaking     | Startup Engineering |
    | aRTup           | Startup Engineering | Public Speaking |

  @wired @failing
  Scenario: Should render course details on clicking a course
    Given I enter Machine Learning in course search box
    Given I click on Machine Learning course
    Then I should see Machine Learning course details
