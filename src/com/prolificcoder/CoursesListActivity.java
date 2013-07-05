package com.prolificcoder;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class CoursesListActivity extends ListActivity {
	private static final int ACTIVITY_DETAIL = 1;

	private EditText filterText = null;
	CourseRowAdaptor adaptor = null;
	private List<ParseObject> courses;
	private Dialog progressDialog;
	CourseRow courseRows[];
	ListView listView1;

	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {

		// Override this method to do custom remote calls
		protected Void doInBackground(Void... params) {
			// Gets the current list of courses in sorted order
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
					"courses");
			query.orderByDescending("upvote");

			try {
				courses = query.find();
			} catch (ParseException e) {

			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			CoursesListActivity.this.progressDialog = ProgressDialog.show(
					CoursesListActivity.this, "", "Loading...", true);
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {

			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Void result) {
			// Put the list of courses into the list view
			courseRows = new CourseRow[courses.size()];
			int i = 0;
			for (ParseObject course : courses) {
				courseRows[i] = (new CourseRow(course.getString("name"),
						Helpers.average(course.getInt("upvote"),
								course.getInt("downvote"))));
				i++;
			}

			adaptor = new CourseRowAdaptor(CoursesListActivity.this,
					R.layout.course_row, courseRows);

			CoursesListActivity.this.progressDialog.dismiss();
			filterText = (EditText) findViewById(R.id.search_box);
			filterText.addTextChangedListener(filterTextWatcher);

			listView1 = (ListView) findViewById(android.R.id.list);
			listView1.setAdapter(adaptor);
			listView1.setVisibility(View.VISIBLE);
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		filterText.removeTextChangedListener(filterTextWatcher);
	}

	private TextWatcher filterTextWatcher = new TextWatcher() {

		public void afterTextChanged(Editable s) {
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			int textlength = filterText.getText().length();
			ArrayList<CourseRow> array_sort = new ArrayList<CourseRow>();
			array_sort.clear();
			for (int i = 0; i < courseRows.length; i++) {
				if (textlength <= courseRows[i].Name.length()) {
					if (filterText
							.getText()
							.toString()
							.equalsIgnoreCase(
									(String) courseRows[i].Name.subSequence(0,
											textlength))) {
						array_sort.add(courseRows[i]);
					}
				}
			}
			CourseRow[] filtered = new CourseRow[array_sort.size()];
			int i = 0;
			for (CourseRow course: array_sort)
			{
				filtered[i]=(CourseRow) course;
				i++;
			}			
			listView1.setAdapter(new CourseRowAdaptor(CoursesListActivity.this,
					R.layout.course_row, filtered));
		}

	};

	@Override
	public void onRestart() {
		super.onRestart();
		new RemoteDataTask().execute();
		for (ParseObject course : courses) {
			course.fetchInBackground(new GetCallback<ParseObject>() {
				public void done(ParseObject object, ParseException e) {
					if (e == null) {
						// Success!
					} else {
						// Failure!
					}
				}
			});
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
		i.putExtra("desc", courses.get(position).getString("Description"));
		startActivityForResult(i, ACTIVITY_DETAIL);
	}
}
