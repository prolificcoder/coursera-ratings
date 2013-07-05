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
    var query = new Parse.Query("courses_db");
    query.equalTo("University", request.params.University);
    query.find({
        success: function(results){
            var courses =[];
            for(var i =0; i<results.length; i++){
                courses.push(results[i].get("name"));
            }
            response.success(courses);
        },
        error: function() {
            response.error("no university for this course");
        }
    });
});
