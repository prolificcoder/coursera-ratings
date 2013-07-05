class AndroidWorld
  def initialize
    android_app = {
        :app_path => ANDROID_PATH,
        :app_package => 'com.prolificcoder',
        :app_activity => '.CoursesListActivity',
        :app_wait_activity => '.CoursesListActivity',
        :app_name => ANDROID_APP_NAME
    }
    if(ENV['runon'] == 'sauce')
      android_app[:app_path] = 'sauce-storage:' + ANDROID_APP_NAME
      android_app[:sauce_username] = SAUCE_USERNAME
      android_app[:sauce_access_key] = SAUCE_ACCESS_KEY
    end
    Appium::Driver.new(android_app).start_driver
  end

  def should_see(element_name)
    s_text element_name
  end

  def platform_click(element_text)
    (s_text element_text).click
  end

  def course_details
    self.class::CourseDetails
  end

end

