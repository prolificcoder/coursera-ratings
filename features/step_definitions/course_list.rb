Given(/^That I open coursera application on my phone$/) do
  wait_true {not exists { s_text "Loading"} }
end
Then(/^I should see all the courses tha are available on coursera$/) do
  pending
end