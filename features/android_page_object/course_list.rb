class AndroidWorld
  module CourseList
    class << self
      def search_box
        find_element(:name, 'Course Search Box')
      end
      def course(course_name)
        find_element(:name, course_name)
      end

      #Course rating cd is of the format - 'Startup Engineering rating'
      def rating(course_name)
        find_element(:name, course_name + ' rating')
      end
    end
  end
end