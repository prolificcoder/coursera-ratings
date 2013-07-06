Given(/^I enter (.*) in search box$/) do |input_text|
  course_list.search_box.send_keys(input_text)
end