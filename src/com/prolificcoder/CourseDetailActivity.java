package com.prolificcoder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class CourseDetailActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.single_list_item_view);

		Intent i = getIntent();
		final String courseName = i.getStringExtra("name");
		int upvote = i.getIntExtra("upvote", 0);
		int downvote = i.getIntExtra("downvote", 0);
		String url = i.getStringExtra("url");

		TextView nameText = (TextView) findViewById(R.id.CourseName);
		nameText.setText(courseName);

		TextView urlText = (TextView) findViewById(R.id.Url);
		urlText.setText((url != null) ? url : "None specified");

		TextView ratingText = (TextView) findViewById(R.id.Rating);
		ratingText.setText(Helpers.average(upvote, downvote).toString());
		
		TextView descText = (TextView) findViewById(R.id.Description);
		descText.setText(i.getStringExtra("desc"));
	

		final ImageButton buttonUp = (ImageButton) findViewById(R.id.Up);
		buttonUp.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ParseQuery<ParseObject> query = ParseQuery.getQuery("courses");
				query.whereEqualTo("name", courseName);
				query.getFirstInBackground(new GetCallback<ParseObject>() {
					  public void done(ParseObject object, ParseException e) {
					    if (object == null) {
					      Log.d("score", "The getFirst request failed.");
					    } else {
					      Log.d("score", "Retrieved the object.");
					      object.increment("upvote");
					      object.saveInBackground();
					    }
					  }
				});
			}
		});

		final ImageButton buttonDown = (ImageButton) findViewById(R.id.Down);
		buttonDown.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ParseQuery<ParseObject> query = ParseQuery.getQuery("courses");
				query.whereEqualTo("name", courseName);
				query.getFirstInBackground(new GetCallback<ParseObject>() {
					  public void done(ParseObject object, ParseException e) {
					    if (object == null) {
					      Log.d("score", "The getFirst request failed.");
					    } else {
					      Log.d("score", "Retrieved the object.");
					       object.increment("downvote");
					       object.saveInBackground();
					    }
					  }
				});
			}
		});
	}
}
