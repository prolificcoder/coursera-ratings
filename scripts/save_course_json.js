var request = require('request');
var Parse = require('parse').Parse;
Parse.initialize("yCuUqabpEb7BHYRtits6iaOs740jpon1Eq6VhQCz", "Lm9XQz0xo1ORP0OvCHBZs8EFq6RJZJQwxMzqqTjA", "GPxPibULRJqKAZ0ik2LNG3nWBozfxUeS5C0Mtsw6");
//var client = new Client('https://www.coursera.org/maestro/api/topic/information?topic-id=compdata');

request({
    uri: "https://www.coursera.org/maestro/api/topic/information?topic-id=compdata",
    method: "GET",
    json: true
}, function (err, res, course) {
    console.log(course.about_the_course);
    console.log(course.estimated_class_workload);
    console.log(course.universities[0].name);
    json_save(course);
});

function json_save(course) {
    var Courses = Parse.Object.extend("courses_db");
    var local_course = new Courses();
    local_course.set("name", course.name);
    local_course.set("Description", course.about_the_course);
    local_course.set("work_load", course.estimated_class_workload);

    var query = new Parse.Query(Courses);
    query.equalTo("name", course.name);
    query.count({
        success: function (number) {
            if (number == 0) {
                local_course.save(null, {
                    success: function (cour) {
                        // Execute any logic that should take place after the object is saved.
                        console.log('New object created with objectId: ' + cour.id);
                        console.log('New object created with objectId: ' + cour.get("Description"));
                        console.log('New object created with objectId: ' + cour.get("work_load"));
                    },
                    error: function (course, error) {
                        // Execute any logic that should take place if the save fails.
                        // error is a Parse.Error with an error code and description.
                        console.log('Failed to create new object, with error code: ' + error.description);
                    }
                });
            }
        }
    })

}
