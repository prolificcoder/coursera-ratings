package com.prolificcoder;

import android.app.Application;

import com.parse.Parse;

public class CoursesListApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, "yCuUqabpEb7BHYRtits6iaOs740jpon1Eq6VhQCz", "tY2jObzWz8HrrIT1GVen71FeHAAVpBrMlpIm6Uu5"); 

	}

}
