package com.parse.demo;

import com.parse.Parse;
import com.parse.ParseACL;

import com.parse.ParseUser;

import android.app.Application;

public class ToDoListApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		Parse.initialize(this, "yCuUqabpEb7BHYRtits6iaOs740jpon1Eq6VhQCz", "tY2jObzWz8HrrIT1GVen71FeHAAVpBrMlpIm6Uu5"); 

		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
		// Optionally enable public read access.
		// defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);
	}

}
