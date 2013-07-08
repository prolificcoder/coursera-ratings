package com.prolificcoder;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

public class UniversityDetailActivity extends FragmentActivity {

	private Dialog progressDialog;
	CourseRow[] courseRows;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.single_university_item_view);

		Intent i = getIntent();
		final String universityShortName = i.getStringExtra("short_name");
		final String universityName = i.getStringExtra("name");

		TextView nameText = (TextView) findViewById(R.id.UniversityName);
		nameText.setText(universityName);
		nameText.setContentDescription(universityName);
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("university", universityShortName);
		ParseCloud.callFunctionInBackground("courses_for_university",
				inputParams, new FunctionCallback<JSONArray>() {
					@Override
					public void done(JSONArray jarray, ParseException e) {
						try {
							courseRows = new CourseRow[jarray.length()];
							for (int i = 0; i < jarray.length(); i++) {
								courseRows[i] = new CourseRow(jarray
										.getString(i), 20);
							}
							CourseRowAdaptor adaptor = new CourseRowAdaptor(UniversityDetailActivity.this,
									R.layout.course_row, courseRows);
                            
							if (jarray.length() != 0)
							{
								ListView listView1 = (ListView) UniversityDetailActivity.this.findViewById(android.R.id.list);
								listView1.setAdapter(adaptor);
								listView1.setVisibility(View.VISIBLE);
							}
							else
							{
								TextView message = (TextView) findViewById(R.id.NoCourseMessage);
								message.setText("No courses are currently offered by this university");
								message.setVisibility(View.VISIBLE);
							}
						}
						catch (JSONException e1) {
							e1.printStackTrace();
						}
					}
				});		
	}
}
