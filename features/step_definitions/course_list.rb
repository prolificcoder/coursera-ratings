Given(/^That I open coursera application on my phone$/) do
  wait_true {not exists { s_text 'Loading'} }
end

Then(/^I should see all the courses tha are available on coursera$/) do
  #HACK: For now verifying a few known courses, proper way is to cross check
  # with coursera API/Website
  s_text 'Startup Engineering'
  s_text 'Machine Learning'
  s_text 'Mental Health'
end

Then(/^I should see the (.*) course$/) do |course_name|
  find_element(:name, course_name )
end

When(/^I should not see (.*) course$/) do |course_name|
  exists { find_element(:name, course_name ) }.must_equal false
end

Given(/^I click on (.*) course$/) do |course_name|
  course_list.course(course_name).click
end
When(/^I should also see (.*) rating$/) do |course_name|
  course_list.rating(course_name)
end