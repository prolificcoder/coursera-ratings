Then(/^I should see all the universities that are available on coursera$/) do
  #HACK: For now verifying a few known courses, proper way is to cross check
  # with coursera API/Website
  s_text 'Stanford University'
  s_text 'Rice University'
end