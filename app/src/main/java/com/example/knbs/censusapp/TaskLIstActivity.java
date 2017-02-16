package com.example.knbs.censusapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.knbs.censusapp.POJO.TaskModel;
import com.example.knbs.censusapp.adapters.TasksAdapter;
import com.example.knbs.censusapp.api.ApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.knbs.censusapp.EnumeratorIDActivity.BASE_URL;

public class TaskLIstActivity extends AppCompatActivity {

    private static String TAG= "TASKLIST_DEGUG";
    ListView lvTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getTasks();


    }

    /**
     * get the tasks from the server
     */
    private void getTasks(){

        lvTaskList = (ListView) findViewById(R.id.lvTask);

        TokenManagement tknMgmt = new TokenManagement(this);
        String userToken = tknMgmt.getToken();

        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //.client(client.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiService restAPI = retrofit.create(ApiService.class);

        Call<List<TaskModel>> call = restAPI.getTaskList(LoginActivity.USER_EMAIL,userToken);
        call.enqueue(new Callback<List<TaskModel>>() {

            @Override
            public void onResponse(Call<List<TaskModel>> call, Response<List<TaskModel>> response) {
                int code = response.code();
                Log.i(TAG,"server response code"+code);

                if(response.isSuccessful()){
                    for(int i =0; i<response.body().size();i++){
                        Log.d(TAG,"tasks"+response.body().get(i).getTaskName());
                    }

                    renderScreen(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<TaskModel>> call, Throwable t) {
                Log.e("TAG", "in Failure stack");
                t.printStackTrace();
            }
        });
    }

    /**
     * render the task list screen
     */
    private void renderScreen(List<TaskModel> taskDetails){
        Log.d(TAG, "in renderScreen");

       /* ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.task_cell, taskDetails);
        adapter.clear();
        adapter.addAll(taskDetails);
        lvTaskList.setAdapter(adapter);*/

        TaskModel [] array = new TaskModel[taskDetails.size()];
        for(int i = 0; i < taskDetails.size(); i++) array[i] = taskDetails.get(i);

        TasksAdapter adapter = new TasksAdapter(this,
                R.layout.task_cell, array);
        View header = getLayoutInflater().inflate(R.layout.task_list_header, null);
        lvTaskList.addHeaderView(header);
        lvTaskList.setAdapter(adapter);

    }

}
