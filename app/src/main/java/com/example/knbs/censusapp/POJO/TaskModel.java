package com.example.knbs.censusapp.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by killer on 1/19/17.
 */



public class TaskModel {

    @SerializedName("task_id")
    @Expose
    private String taskId;
    @SerializedName("enumerator_id")
    @Expose
    private String enumeratorId;
    @SerializedName("task_name")
    @Expose
    private String taskName;
    @SerializedName("task_duration")
    @Expose
    private String taskDuration;
    @SerializedName("remember_token")
    @Expose
    private Object rememberToken;
    private String status;
    @SerializedName("post_status")
    @Expose
    private String postStatus;
    @SerializedName("date")
    @Expose
    private String date;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getEnumeratorId() {
        return enumeratorId;
    }

    public void setEnumeratorId(String enumeratorId) {
        this.enumeratorId = enumeratorId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDuration() {
        return taskDuration;
    }

    public void setTaskDuration(String taskDuration) {
        this.taskDuration = taskDuration;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getPostStatus() {
        return postStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Object getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(Object rememberToken) {
        this.rememberToken = rememberToken;
    }

}

