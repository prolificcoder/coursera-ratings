Parse.Cloud.define("rating", function(request, response) {
  var query = new Parse.Query("courses");
  query.equalTo("name", request.params.course);
  query.find({
    success: function(results) {
      var sum = results[0].get("upvote") + results[0].get("downvote");
      response.success(results[0].get("upvote") / sum);
    },
    error: function() {
      response.error("course lookup failed");
    }
  });
});

Parse.Cloud.define("courses_for_university", function(request, response) {
    var query = new Parse.Query("courses");
    query.equalTo("University", request.params.university);
    query.find({
        success: function(results){
            var courses =[];
            for(var i =0; i<results.length; i++){
                courses.push(results[i].get("name"));
                courses.push(results[i].get("upvote"));
                courses.push(results[i].get("downvote"));
            }
            response.success(courses);
        },
        error: function() {
            response.error("no courses for this university");
        }
    });
});
Parse.Cloud.define("courses_for_category", function(request, response) {
    var query = new Parse.Query("courses");
    query.equalTo("Categories", request.params.category);
    query.find({
        success: function(results){
            var courses =[];
            for(var i =0; i<results.length; i++){
                courses.push(results[i].get("name"));
                courses.push(results[i].get("upvote"));
                courses.push(results[i].get("downvote"));
            }
            response.success(courses);
        },
        error: function(error) {
            response.error(console.log(error));
        }
    });
});
