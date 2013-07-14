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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class UniversityListFragment extends ListFragment{
	private static final int ACTIVITY_DETAIL = 1;

	private EditText filterText = null;
	private List<ParseObject> universities;
	private Dialog progressDialog;
	ArrayList<String> universityRows;
	ListView listView1;
	View rootView;

	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {

		// Override this method to do custom remote calls
		protected Void doInBackground(Void... params) {
			// Gets the current list of courses in sorted order
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
					Constants.PARSE_UNIVERSITY_TABLE_NAME);

			try {
				universities = query.find();
			} catch (ParseException e) {

			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			UniversityListFragment.this.progressDialog = ProgressDialog.show(
					getActivity(), "", "Loading...", true);
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {

			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Void result) {
			// Put the list of todos into the list view
			universityRows = new ArrayList<String>();
			for (ParseObject university : universities) {
				universityRows.add(university.getString("name"));
			}

			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, universityRows);
			UniversityListFragment.this.progressDialog.dismiss();
			filterText = (EditText) rootView.findViewById(R.id.university_search_box);
			filterText.addTextChangedListener(filterTextWatcher);

			listView1 = (ListView) rootView.findViewById(android.R.id.list);
			listView1.setAdapter(arrayAdapter);
			listView1.setVisibility(View.VISIBLE);
		}
	}

	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
           Bundle savedInstanceState) {
	   super.onCreateView(inflater, container, savedInstanceState);	
       rootView = inflater.inflate(R.layout.fragment_university_list, container, false);
       new RemoteDataTask().execute();
       return rootView;
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
			ArrayList<String> array_sort = new ArrayList<String>();
			array_sort.clear();
			for (int i = 0; i < universityRows.size(); i++) {
				if (textlength <= universityRows.get(i).length()) {
					if (universityRows.get(i).toLowerCase().contains(filterText.getText().toString().toLowerCase()))
			     	 {
						array_sort.add(universityRows.get(i));
					}
				}
			}
			
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, array_sort);	
			listView1.setAdapter(arrayAdapter);
		}

	};



	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);		
		Intent i = new Intent(getActivity(), UniversityDetailActivity.class);
		i.putExtra("name", l.getItemAtPosition(position).toString());
		startActivityForResult(i, ACTIVITY_DETAIL);
	}
}
