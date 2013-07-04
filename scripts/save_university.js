var request = require('request');
var Parse = require('parse').Parse;
Parse.initialize("yCuUqabpEb7BHYRtits6iaOs740jpon1Eq6VhQCz", "Lm9XQz0xo1ORP0OvCHBZs8EFq6RJZJQwxMzqqTjA", "GPxPibULRJqKAZ0ik2LNG3nWBozfxUeS5C0Mtsw6");

request({
    uri: "https://www.coursera.org/maestro/api/university/list",
    method: "GET",
    json: true
}, function (err, res, universities) {
    for(var i=0; i<universities.length; i++) {
        save_university(universities[i]);
    }
});

function save_university(university) {
    var Universities = Parse.Object.extend("universities_te");
    var local_university = new Universities();
    local_university.set("name", university.name);
    local_university.set("short_name", university.short_name);
    local_university.set("favicon", university.favicon);
    local_university.set("class_logo", university.class_logo);

    var query = new Parse.Query(Universities);
    query.equalTo("name", university.name);
    query.count({
        success: function (number) {
            if (number == 0) {
                local_university.save(null, {
                    success: function (cour) {
                        // Execute any logic that should take place after the object is saved.
                        console.log('New object created with objectId: ' + cour.id);
                    },
                    error: function (university, error) {
                        // Execute any logic that should take place if the save fails.
                        // error is a Parse.Error with an error code and description.
                        console.log('Failed to create new object, with error code: ' + error.description);
                    }
                });
            }
        }
    })

}