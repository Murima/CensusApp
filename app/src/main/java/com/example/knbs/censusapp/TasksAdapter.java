package com.example.knbs.censusapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.knbs.censusapp.POJO.TaskModel;
import com.google.android.gms.tasks.Task;

/**
 * Created by killer on 1/23/17.
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

            row.setTag(holder);

        }
        else
        {
            holder = (TaskHolder)row.getTag();
        }
        TaskModel task = data[position];
        holder.tvName.setText(task.getTaskName());
        holder.tvID.setText(task.getTaskId());
        holder.tvDuration.setText(task.getTaskDuration());

        return row;
    }

    static class TaskHolder{

        TextView tvName;
        TextView tvID;
        TextView tvDuration;

    }

}
