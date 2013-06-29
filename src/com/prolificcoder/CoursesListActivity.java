package com.prolificcoder;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class CoursesListActivity extends ListActivity{
	private static final int ACTIVITY_DETAIL = 1;


	private List<ParseObject> courses;
	private Dialog progressDialog;
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
		
		// Override this method to do custom remote calls
		protected Void doInBackground(Void... params) {
			// Gets the current list of courses in sorted order
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("courses");
			query.orderByDescending("_created_at");

			try {
				courses = query.find();
			} catch (ParseException e) {

			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			CoursesListActivity.this.progressDialog = ProgressDialog.show(CoursesListActivity.this, "",
					"Loading...", true);
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {

			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Void result) {
			// Put the list of todos into the list view
			CourseRow courseRows[] =  new CourseRow[courses.size()];
			int i = 0;
			for (ParseObject course : courses) {
				courseRows[i] = (new CourseRow(course.getString("name"), Helpers.average(course.getInt("upvote"),course.getInt("downvote"))));
				i++;
			}
			
			CourseRowAdaptor adaptor = new CourseRowAdaptor(CoursesListActivity.this, R.layout.course_row, courseRows);

			CoursesListActivity.this.progressDialog.dismiss();

			ListView listView1 = (ListView)findViewById(android.R.id.list);
			listView1.setAdapter(adaptor);
			listView1.setVisibility(View.VISIBLE);
		}

		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		TextView empty = (TextView) findViewById(android.R.id.empty);
		empty.setVisibility(View.INVISIBLE);

		new RemoteDataTask().execute();
		registerForContextMenu(getListView());
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(this, CourseDetailActivity.class);
		i.putExtra("name", courses.get(position).getString("name").toString());
		i.putExtra("upvote", courses.get(position).getInt("upvote"));
		i.putExtra("downvote", courses.get(position).getInt("downvote"));
		i.putExtra("url", courses.get(position).getString("url"));
		startActivityForResult(i, ACTIVITY_DETAIL);
	}
}
