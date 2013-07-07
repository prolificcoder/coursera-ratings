package com.prolificcoder;

import java.util.List;

import android.app.Activity;
import android.util.Log;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class Helpers {
	
	public static final int VoteButton_up = 1;
	public static final int VoteButton_down = 2;
	public static Integer average(int upvotes, int downvotes) {
		int total = upvotes + downvotes;
		if (total == 0)
			return 0;
		return (int) (((float) upvotes / total) * 100);
	}

	public static void facebook_login(Activity activity) {
		ParseFacebookUtils.logIn(activity, new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException err) {
				if (user == null) {
					Log.d("MyApp",
							"Uh oh. The user cancelled the Facebook login.");
				} else if (user.isNew()) {
					Log.d("MyApp",
							"User signed up and logged in through Facebook!");
				} else {
					Log.d("MyApp", "User logged in through Facebook!");
				}
			}
		});
	}
	
	public static void save_vote(final String courseName, String vote) {
		ParseObject userMap = new ParseObject("user_course_map");
		userMap.put("username", ParseUser.getCurrentUser().getUsername());
		userMap.put("coursename", courseName);
		userMap.put("vote", vote);
		userMap.saveInBackground();
		Log.d("from app", vote);
	}
	
	public static Vote vote_status(final String courseName){
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("user_course_map");
		query.whereContains("username", ParseUser.getCurrentUser().getUsername());
		query.whereContains("coursename", courseName);
		List<ParseObject> vote;
		try {
			vote = query.find();
			if (vote.size() == 0)
				return Vote.NONE;
			else if(vote.get(0).getString("vote")=="up")
				return Vote.UP;
			else if(vote.get(0).getString("vote")=="down")
				return Vote.DOWN;
		} catch (ParseException e) {
			Log.d("from app", "vote query exception");
			return Vote.INVALID;
		}
		
	    return Vote.INVALID;
	}
	public enum Vote{
		NONE, UP, DOWN, INVALID;
	}
	
	
}


