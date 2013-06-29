package com.prolificcoder;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;


public class CourseRowAdaptor extends ArrayAdapter<CourseRow>{

	  Context context; 
	    int layoutResourceId;    
	    CourseRow data[] = null;
	    
	    public CourseRowAdaptor(Context context, int layoutResourceId, CourseRow[] data) {
	        super(context, layoutResourceId, data);
	        this.layoutResourceId = layoutResourceId;
	        this.context = context;
	        this.data = data;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View row = convertView;
	        CourseInfoHolder holder = null;
	        
	        if(row == null)
	        {
	            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	            row = inflater.inflate(layoutResourceId, parent, false);
	            
	            holder = new CourseInfoHolder();
	            holder.Name = (TextView)row.findViewById(R.id.Name);
	            holder.Rating = (TextView)row.findViewById(R.id.Rating);	            
	            row.setTag(holder);
	        }
	        else
	        {
	            holder = (CourseInfoHolder)row.getTag();
	        }
	        
	        CourseRow course = data[position];
	        holder.Name.setText(course.Name);
	        holder.Rating.setText(course.Rating.toString());
	        return row;
	    }
	    
	    static class CourseInfoHolder
	    {
	        TextView Name;
	        TextView Rating;
	    }
	}
