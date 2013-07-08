package com.prolificcoder;

import java.util.List;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class Helpers {
	
	public static final int VoteButton_up = 1;
	public static final int VoteButton_down = 2;
	public static final int VoteButton_clear = 3;
	public static Integer average(int upvotes, int downvotes) {
		int total = upvotes + downvotes;
		if (total == 0)
			return 0;
		return (int) (((float) upvotes / total) * 100);
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
			else if(vote.get(0).getString("vote").equalsIgnoreCase("up"))
				return Vote.UP;
			else if(vote.get(0).getString("vote").equalsIgnoreCase("down"))
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
	public static boolean delete_vote(String courseName) {
		Boolean success = true;
		ParseQuery<ParseObject> query = new ParseQuery("user_course_map");
		query.whereContains("coursename", courseName);
		query.whereContains("username", ParseUser.getCurrentUser().getUsername());
		try {
			List<ParseObject> listCourse = query.find();
			if (listCourse.size() == 0)
			{
				success = true;
			}
			else
			{
				listCourse.get(0).delete();
				listCourse.get(0).saveInBackground();
			}
			
		} catch (ParseException e) {
			success = false;
			e.printStackTrace();
			return false;
		}
		
		return success;
	}
	
	
}


