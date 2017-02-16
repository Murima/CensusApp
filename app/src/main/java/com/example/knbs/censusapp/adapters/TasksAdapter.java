package com.example.knbs.censusapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.knbs.censusapp.POJO.TaskModel;
import com.example.knbs.censusapp.R;
import com.google.android.gms.tasks.Task;

/**
 * Get the data and set the text in the appropriate views.
 */

public class TasksAdapter extends ArrayAdapter<TaskModel> {
    Context context;
    int layoutResourceId;
    TaskModel data[] = null;

    public TasksAdapter(Context context, int resource, TaskModel[] data) {
        super(context, resource, data);
        this.layoutResourceId = resource;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TaskHolder holder = null;

        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new TaskHolder();
            holder.tvName = (TextView) row.findViewById(R.id.tvTaskName);
            holder.tvID = (TextView)row.findViewById(R.id.tvTaskID);
            holder.tvDuration= (TextView) row.findViewById(R.id.tvTaskDuration);
            holder.chkStatus= (CheckBox) row.findViewById(R.id.chkStatus);

            row.setTag(holder);

        }
        else
        {
            holder = (TaskHolder)row.getTag();
        }
        //set the text in the task cell

        TaskModel task = data[position];
        holder.tvName.setText(task.getTaskName());
        holder.tvID.setText(task.getTaskId());
        holder.tvDuration.setText(task.getTaskDuration());

        String postStatus = task.getPostStatus();

        if (postStatus.equals("Accepted")){
            holder.tvName.setTextColor(Color.GREEN);
            holder.tvID.setTextColor(Color.GREEN);
            //check status and post status.
            holder.chkStatus.setText("Accepted");
            holder.chkStatus.setTextColor(Color.GREEN);
            holder.chkStatus.setChecked(true);
        }
        else if (postStatus.equals("Rejected")){
            holder.chkStatus.setText("Rejected");
            holder.chkStatus.setTextColor(Color.RED);
            holder.chkStatus.setChecked(false);
        }
        else{


        }
        return row;
    }

    static class TaskHolder{

        TextView tvName;
        TextView tvID;
        TextView tvDuration;
        CheckBox chkStatus;

    }

}
