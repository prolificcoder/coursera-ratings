var request = require('request');
var strip = require('strip');
var Parse = require('parse').Parse;
Parse.initialize("yCuUqabpEb7BHYRtits6iaOs740jpon1Eq6VhQCz", "Lm9XQz0xo1ORP0OvCHBZs8EFq6RJZJQwxMzqqTjA", "GPxPibULRJqKAZ0ik2LNG3nWBozfxUeS5C0Mtsw6");
request({
    uri: "https://www.coursera.org/maestro/api/topic/list",
    method: "GET",
    json: true
}, function (err, res, courses) {

    for(var i=0; i<courses.length; i++) {
        json_save(courses[i]);
    }
});

function json_save(course) {
    request({
            uri: "https://www.coursera.org/maestro/api/topic/information?topic-id=" + course.short_name,
            method: "GET",
            json: true
        }, function (err, res, course) {
        var Courses = Parse.Object.extend("courses_prod");
        var local_course = new Courses();
        local_course.set("name", course.name);
        local_course.set("Description", strip(course.about_the_course));
        local_course.set("work_load", course.estimated_class_workload);
        local_course.set("University", course.universities[0].name);
        if(course.courses.length >0)
            local_course.set("url",course.courses[course.courses.length-1].home_link);
        local_course.set("Categories", extract_short_name(course.categories));
        var query = new Parse.Query(Courses);
        query.equalTo("name", course.name);
        query.count({
            success: function (number) {
                if (number == 0) {
                    local_course.save(null, {
                        success: function (cour) {
                            // Execute any logic that should take place after the object is saved.
                            console.log('New object created with objectId: ' + cour.id);
                        },
                        error: function (course, error) {
                            // Execute any logic that should take place if the save fails.
                            // error is a Parse.Error with an error code and description.
                            console.log('Failed to create new object, with error code: ' + error.description);
                        }
                    });
                }
            }
        });
    });
}

function extract_short_name(object_array) {
    var cat_names = [];
    for (var i = 0; i < object_array.length; i++) {
        cat_names.push(object_array[i].name);
    }
    return cat_names;
}
