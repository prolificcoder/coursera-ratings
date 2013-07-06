Given(/^I enter (.*)  in search box$/) do |input_text|
  course_list.search_box.setText(input_text)
end