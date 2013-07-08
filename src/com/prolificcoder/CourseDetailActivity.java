package com.prolificcoder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.prolificcoder.Helpers.Vote;

public class CourseDetailActivity extends Activity {

	private String courseName = null;
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		updateRating(requestCode);
	}

	private void updateRating(int requestCode) {
		final int rCode = requestCode;
		if (Helpers.vote_status(courseName) == Vote.NONE) {
			ParseQuery<ParseObject> query = ParseQuery
					.getQuery("courses");
			query.whereEqualTo("name", courseName);
			query.getFirstInBackground(new GetCallback<ParseObject>() {
				public void done(ParseObject object, ParseException e) {
					if (object == null) {
						Log.d("from app", "The getFirst request failed.");
					} else {
						Log.d("from app", "Retrieved the object.");
						if (rCode == Helpers.VoteButton_up)
						{
						object.increment("upvote");
						}
						else if(rCode == Helpers.VoteButton_down)
						{
							object.increment("downvote");
						}
						object.saveInBackground();
						TextView ratingText = (TextView) findViewById(R.id.Rating);
						ratingText.setText(Helpers.average(
								object.getInt("upvote"),
								object.getInt("downvote")).toString()
								+ "%");
					}
				}
			});
			if (rCode == Helpers.VoteButton_up)
			{
				Helpers.save_vote(courseName, "up");
			}
			else if(rCode == Helpers.VoteButton_down)
			{
				Helpers.save_vote(courseName, "down");
			}
			
		}
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.single_course_item_view);

		Intent i = getIntent();
		courseName = i.getStringExtra("name");
		int upvote = i.getIntExtra("upvote", 0);
		int downvote = i.getIntExtra("downvote", 0);
		String url = i.getStringExtra("url");

		TextView nameText = (TextView) findViewById(R.id.CourseName);
		nameText.setText(courseName);
		nameText.setContentDescription(courseName);

		TextView urlText = (TextView) findViewById(R.id.Url);
		urlText.setText((url != null) ? url : "None specified");

		TextView ratingText = (TextView) findViewById(R.id.Rating);
		ratingText.setText(Helpers.average(upvote, downvote).toString() + "%");

		TextView descText = (TextView) findViewById(R.id.Description);
		descText.setText(i.getStringExtra("desc"));

		final ImageButton buttonUp = (ImageButton) findViewById(R.id.Up);
		buttonUp.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (ParseUser.getCurrentUser() == null)
				{
					startActivityForResult(new Intent(
							CourseDetailActivity.this, LoginActivity.class), Helpers.VoteButton_up);					
				}	
				else
				{
				   updateRating(Helpers.VoteButton_up);
				}
			}
		});

		final ImageButton buttonDown = (ImageButton) findViewById(R.id.Down);
		buttonDown.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (ParseUser.getCurrentUser() == null)
				{
					startActivityForResult(new Intent(
							CourseDetailActivity.this, LoginActivity.class), Helpers.VoteButton_down);					
				}
				else
				{
				   updateRating(Helpers.VoteButton_down);
				}
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.common_menu, menu);
	    return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.login:
	        Intent i = new Intent(this, LoginActivity.class);
	        startActivityForResult(i,0);
	        return true;
	    case R.id.logout:
	    	ParseUser.logOut();
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
}
