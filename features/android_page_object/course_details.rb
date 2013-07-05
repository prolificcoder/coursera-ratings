class AndroidWorld
  module CourseDetails
    class << self
      def up_vote
        find_element(:name, 'Up Vote')
      end
      def down_vote
        find_element(:name, 'Down Vote')
      end
      def rating
        find_element(:name, 'Rating')
      end
      def course_url
        find_element(:name, 'Course Url')
      end
      def course_description
        find_element(:name, 'Course Description')
      end
      def course_name(course_name)
        find_element(:name, course_name)
      end
    end
  end
end