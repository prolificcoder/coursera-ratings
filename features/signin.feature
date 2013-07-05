Scenario: A user should be able to login with facebook
  Given That I click on menu
    And  I am not logged in
  Then I should see Connect with Facebook
    And I click on Connect with Facebook
  Then I should be able to login as a Facebook user

Scenario: A user should be able to logout of facebook
  Given That I click on menu
    And  I am logged in
  Then I should see Disconnect from Facebook
    And I click on Disconnect from Facebook
  Then I should be able to logout
    And I click on menu
  Then I should see Connect with Facebook
