package com.prolificcoder;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

public class CoursesListApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		Parse.initialize(this, "yCuUqabpEb7BHYRtits6iaOs740jpon1Eq6VhQCz", "tY2jObzWz8HrrIT1GVen71FeHAAVpBrMlpIm6Uu5"); 
		ParseFacebookUtils.initialize("627867653903494");
		try {
			ParseUser.logIn("satya", "pass");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ParseACL defaultACL = new ParseACL();
		// Optionally enable public read access.
		// defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);
	}

}
