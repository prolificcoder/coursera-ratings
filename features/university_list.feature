Feature: As a coursera user/enthusiast, I'd
  like to look at all the universities in coursera

  Background: App is loaded
    Given That I open coursera application on my phone
      And I click on the universities tab

  @passing
  Scenario: All the universities on coursera are listed
    Then I should see all the universities that are available on coursera

  Scenario Outline: Universities can be searched
    Given I enter <Input Text>  in university search box
    Then I should see <University Name> course
    But I should not see <Negative Text> course
  Examples:
    | Input Text   | University Name            | Negative Text |
    | Stanford     | Stanford University        | University of Washington |
    | Washington   | University of Washington   | Stanford University|
    | stANfoRd     | Stanford University        | University of Washington |
    | WAShinGton   | University of Washington   | Stanford University|

  Scenario: Should render university details on clicking a university
    Given I click on Stanford university
    Then I should see Stanford university details
    And I should see Stanford university courses list
