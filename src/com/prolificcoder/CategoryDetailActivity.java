package com.prolificcoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class CategoryDetailActivity extends ListActivity {

	private Dialog progressDialog;
	CourseRow[] courseRows;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.single_category_item_view);

		Intent i = getIntent();
		// i.getStringExtra("short_name");
        final String categoryName = i.getStringExtra("name");
        
		TextView nameText = (TextView) findViewById(R.id.CategoryName);
		nameText.setText(categoryName);
		nameText.setContentDescription(categoryName);
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("category", categoryName);
		ParseCloud.callFunctionInBackground("courses_for_category",
				inputParams, new FunctionCallback<JSONArray>() {
					@Override
					public void done(JSONArray jarray, ParseException e) {
						try {
							courseRows = new CourseRow[jarray.length() / 3];
							int i = 0;
							int cRowCount = 0;
							while(i < jarray.length()) {
								
								String courseName = jarray.getString(i++);
								String upvoteString = jarray.getString(i++);
								String downvoteString = jarray.getString(i++);
								int upvote = (!upvoteString.equalsIgnoreCase("null")) ? Integer.getInteger(upvoteString) : 0;
								int downvote = (!downvoteString.equalsIgnoreCase("null")) ? Integer.getInteger(downvoteString) : 0;
								courseRows[cRowCount++] = new CourseRow(courseName, Helpers.average(upvote, downvote));
							}
							CourseRowAdaptor adaptor = new CourseRowAdaptor(CategoryDetailActivity.this,
									R.layout.course_row, courseRows);

							if (jarray.length() != 0)
							{
								ListView listView1 = (ListView) CategoryDetailActivity.this.findViewById(android.R.id.list);
								listView1.setAdapter(adaptor);
								listView1.setVisibility(View.VISIBLE);
							}
							else
							{
								TextView message = (TextView) findViewById(R.id.NoCourseMessage);
								message.setText("No courses are classified under this category");
								message.setVisibility(View.VISIBLE);
							}

						} catch (JSONException e1) {
							e1.printStackTrace();
						}
					}
				});		
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		CourseRow c = (CourseRow)l.getItemAtPosition(position);
		Intent intent = new Intent(this, CourseDetailActivity.class);
		
		intent.putExtra("coursename", c.Name);
		startActivityForResult(intent, 1);
	}
}
