package com.prolificcoder;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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
		final Vote voteStatus = Helpers.vote_status(courseName);
		if ((voteStatus == Vote.NONE) || (rCode == Helpers.VoteButton_clear))
		{
			ParseQuery<ParseObject> query = ParseQuery
					.getQuery(Constants.PARSE_COURSE_TABLE_NAME);
			query.whereEqualTo("name", courseName);
			
			query.getFirstInBackground(new GetCallback<ParseObject>() {
				
				public void done(ParseObject object, ParseException e) {
						if (rCode == Helpers.VoteButton_up)
						{
							if (voteStatus == Vote.NONE)
							{
								object.increment("upvote");
								Helpers.save_vote(courseName, "up");
							}
						}
						else if(rCode == Helpers.VoteButton_down)
						{
							if (Helpers.vote_status(courseName) == Vote.NONE)
							{
								object.increment("downvote");
								Helpers.save_vote(courseName, "down");
							}
						}						
						else if (rCode == Helpers.VoteButton_clear)
						{	
							if (voteStatus != Vote.NONE)
							{
								Helpers.delete_vote(courseName);
							}
							
							if (voteStatus == Vote.UP)
						    {
						       int count = object.getInt("upvote");
						       object.put("upvote", --count);
						    }
						    else if(voteStatus == Vote.DOWN)
						    {
						    	int count = object.getInt("downvote");
							    object.put("downvote", --count);
						    }
						}
						object.saveInBackground();
						TextView ratingText = (TextView) findViewById(R.id.Rating);
						ratingText.setText(Helpers.average(
								object.getInt("upvote"),
								object.getInt("downvote")).toString()
								+ "%");
				}
			});
			
			
		}
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.single_course_item_view);

		Intent i = getIntent();
		courseName = i.getStringExtra("coursename");
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery(Constants.PARSE_COURSE_TABLE_NAME);
		query.whereContains("name", courseName);
		List<ParseObject> course_details = null;
		try {
			course_details = query.find();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int upvote = course_details.get(0).getInt("upvote");
		int downvote =  course_details.get(0).getInt("downvote");
		String url =  course_details.get(0).getString("url");
		String university =  course_details.get(0).getString("University");
		String description = course_details.get(0).getString("Description");
		List<String> categories = course_details.get(0).getList("Categories");
		String catString = android.text.TextUtils.join(", ", categories);
		
		TextView nameText = (TextView) findViewById(R.id.CourseName);
		nameText.setText(courseName);
		nameText.setContentDescription(courseName);

		TextView urlText = (TextView) findViewById(R.id.Url);
		urlText.setText((url != null) ? url : "None specified");

		TextView ratingText = (TextView) findViewById(R.id.Rating);
		ratingText.setText(Helpers.average(upvote, downvote).toString() + "%");

		TextView descText = (TextView) findViewById(R.id.Description);
		
		descText.setMovementMethod(new ScrollingMovementMethod());
		descText.setText(description);

		
		TextView universityView = (TextView) findViewById(R.id.University);
		universityView.setText((CharSequence) university);
	
		TextView categoryView = (TextView) findViewById(R.id.Categories);
		categoryView.setText(catString);
		
		TextView clearyourvote = (TextView)findViewById(R.id.ClearVote);
		clearyourvote.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (ParseUser.getCurrentUser() == null)
				{
					startActivityForResult(new Intent(
							CourseDetailActivity.this, LoginActivity.class), Helpers.VoteButton_clear);					
				}	
				else
				{
					updateRating(Helpers.VoteButton_clear);
				}
			}
		});

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
