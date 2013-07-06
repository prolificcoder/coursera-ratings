class AndroidWorld
  module CourseList
    class << self
      def search_box
        find_element(:name, 'Course Search Box')
      end
    end
  end
end