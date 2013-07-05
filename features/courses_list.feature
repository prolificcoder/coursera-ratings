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

  Scenario Outline: Courses can be searched
    Given I enter <Input Text>  in search box
    Then I should see <Output Text> course
    But I should not see <Negative Text> course
  Examples:
    | Input Text          | Output Text         | Negative Text |
    | Public Speaking     | Public Speaking     | Startup Engineering |
    | Startup Engineering | Startup Engineering | Public Speaking |


  Scenario: Courses can be searched case-insensitively
    Given I enter staRTup engiNEering in search box
    Then I should see Startup Engineering course
      But I should not see Public Speaking course

  Scenario: Should render course details on clicking a course
    Given I click on startup engineering
    Then I should see Startup Engineering course details
