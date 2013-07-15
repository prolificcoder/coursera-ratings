var request = require('request');
var Parse = require('parse').Parse;
Parse.initialize("yCuUqabpEb7BHYRtits6iaOs740jpon1Eq6VhQCz", "Lm9XQz0xo1ORP0OvCHBZs8EFq6RJZJQwxMzqqTjA", "GPxPibULRJqKAZ0ik2LNG3nWBozfxUeS5C0Mtsw6");

request({
    uri: "https://www.coursera.org/maestro/api/category/list",
    method: "GET",
    json: true
}, function (err, res, categories) {
    for(var i=0; i<categories.length; i++) {
        save_category(categories[i]);
    }
});

function save_category(category) {
    var categories = Parse.Object.extend("categories");
    var local_category = new categories();
    local_category.set("name", category.name);
    local_category.set("short_name", category.short_name);

    var query = new Parse.Query(categories);
    query.equalTo("name", category.name);
    query.count({
        success: function (number) {
            if (number == 0) {
                local_category.save(null, {
                    success: function (cour) {
                        // Execute any logic that should take place after the object is saved.
                        console.log('New object created with objectId: ' + cour.id);
                    },
                    error: function (category, error) {
                        // Execute any logic that should take place if the save fails.
                        // error is a Parse.Error with an error code and description.
                        console.log('Failed to create new object, with error code: ' + error.description);
                    }
                });
            }
        }
    })

}
