Then(/^I should see url, description, name and ratings of (.*) course$/) do |course_name|
  course_details.course_name(course_name)
  course_details.course_url
  course_details.course_description
  course_details.rating
end

When(/^I should see upvote and downvote$/) do
  course_details.up_vote
  course_details.down_vote
end

Given(/^That I am on (.*) course details view$/) do |course_name|
  (s_text course_name).click
end

Then(/^I should see (.*) course details$/) do |course_name|
  course_details.course_name(course_name)
end