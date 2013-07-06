package com.prolificcoder;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class CourseListFragment extends ListFragment {
	private static final int ACTIVITY_DETAIL = 1;

	private EditText filterText = null;
	CourseRowAdaptor adaptor = null;
	private List<ParseObject> courses;
	private Dialog progressDialog;
	CourseRow courseRows[];
	ListView listView1;
	View rootView;

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
			CourseListFragment.this.progressDialog = ProgressDialog.show(
					getActivity(), "", "Loading...", true);
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

			adaptor = new CourseRowAdaptor(getActivity(),
					R.layout.course_row, courseRows);

			CourseListFragment.this.progressDialog.dismiss();
			filterText = (EditText) rootView.findViewById(R.id.course_search_box);
			filterText.addTextChangedListener(filterTextWatcher);

			listView1 = (ListView) rootView.findViewById(android.R.id.list);
			listView1.setAdapter(adaptor);
			listView1.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public void onDestroy() {
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
			listView1.setAdapter(new CourseRowAdaptor(getActivity(),
					R.layout.course_row, filtered));
		}

	};

//	@Override
//	public void onResume() {
//		super.onResume();
//		new RemoteDataTask().execute();
//    }
	
	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState) {	
		super.onCreateView(inflater, container, savedInstanceState);	
		rootView = inflater.inflate(R.layout.fragment_course_list,
				container, false);
		new RemoteDataTask().execute();
		return rootView;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(getActivity(), CourseDetailActivity.class);
		i.putExtra("name", courses.get(position).getString("name").toString());
		i.putExtra("upvote", courses.get(position).getInt("upvote"));
		i.putExtra("downvote", courses.get(position).getInt("downvote"));
		i.putExtra("url", courses.get(position).getString("url"));
		i.putExtra("desc", courses.get(position).getString("Description"));
		startActivityForResult(i, ACTIVITY_DETAIL);
	}
}
