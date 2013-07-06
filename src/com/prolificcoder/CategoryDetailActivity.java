package com.prolificcoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class CategoryDetailActivity extends FragmentActivity {

	private Dialog progressDialog;
	CourseRow[] courseRows;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.single_category_item_view);

		Intent i = getIntent();
		final String categoryName = i.getStringExtra("name");

		TextView nameText = (TextView) findViewById(R.id.CategoryName);
		nameText.setText(categoryName);
		nameText.setContentDescription(categoryName);
		Map<String,String> inputParams = new HashMap<String,String>();
		inputParams.put("category", categoryName);
		ParseCloud.callFunctionInBackground("courses_for_category", inputParams, new FunctionCallback<JSONArray>(){

			@Override
			public void done(JSONArray jarray, ParseException e) {
			try
			{
	        	 Log.w(null, "Success in calling cloud code");
	        	 Log.w(null, "First course retrieved " + jarray.get(0).toString());
				courseRows = new CourseRow[jarray.length()];
		         for (int i = 0; i < jarray.length(); i++)
			     {		         
		        	courseRows[i] = new CourseRow(jarray.getString(i), 20);
			     }
			     
			}
			catch (JSONException e1)
			{
				e1.printStackTrace();
			}
		}
	});
}
	   
		@Override
		protected void onStart()
		{
			super.onStart();
			if (courseRows != null)
			{
			CourseRowAdaptor adaptor = new CourseRowAdaptor(this,
					R.layout.course_row, courseRows);
			
			ListView listView1 = (ListView) this.findViewById(android.R.id.list);
			listView1.setAdapter(adaptor);
			listView1.setVisibility(View.VISIBLE);
			}
		}
	}

