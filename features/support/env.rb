require 'rspec/expectations'
require 'appium_lib'
require 'pry'
require 'minitest/spec'

SAUCE_USERNAME='smalugu'
SAUCE_ACCESS_KEY='208524b4-4753-464c-814c-d85e6b418fe2'
ANDROID_PATH = '/Users/malugus/Downloads/ToDoList 2/bin/CourseraRatings.apk'
ANDROID_APP_NAME = 'CourseraRatings.apk'

World(MiniTest::Assertions){AndroidWorld.new}
MiniTest::Spec.new(nil)

After { driver_quit }
