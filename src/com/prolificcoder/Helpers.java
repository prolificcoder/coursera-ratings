package com.prolificcoder;

import android.app.Activity;
import android.util.Log;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

public class Helpers {
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
}
