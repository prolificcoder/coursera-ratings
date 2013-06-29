package com.prolificcoder;

import java.util.List;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class CourseDetailActivity extends Activity {

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.single_list_item_view);
		
		Intent i = getIntent();
		String name = i.getStringExtra("name");
		int upvote = i.getIntExtra("upvote", 0);
		int downvote = i.getIntExtra("downvote", 0);
		String url = i.getStringExtra("url");
		
		TextView nameText = (TextView)findViewById(R.id.Name);
		nameText.setText(name);
		
		TextView urlText = (TextView)findViewById(R.id.Url);
		urlText.setText((url != null) ? url : "None specified");
		
		TextView ratingText = (TextView)findViewById(R.id.Rating);
		nameText.setText(Helpers.average(upvote, downvote).toString());
		
	}
	

}
