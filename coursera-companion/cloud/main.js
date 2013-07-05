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
