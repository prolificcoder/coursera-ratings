package com.prolificcoder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
		
		TextView nameText = (TextView)findViewById(R.id.CourseName);
		nameText.setText(name);
		
		TextView urlText = (TextView)findViewById(R.id.Url);
		urlText.setText((url != null) ? url : "None specified");
		
		TextView ratingText = (TextView)findViewById(R.id.Rating);
		ratingText.setText(Helpers.average(upvote, downvote).toString());
		
		final Button buttonUp = (Button) findViewById(R.id.Up);
		buttonUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                
            }
        });
		
        final Button buttonDown = (Button) findViewById(R.id.Down);
        buttonDown.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                
            }
        });
	}
}
