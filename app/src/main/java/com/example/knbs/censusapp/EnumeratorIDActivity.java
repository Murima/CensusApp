package com.example.knbs.censusapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.knbs.censusapp.POJO.AccountModel;
import com.example.knbs.censusapp.api.ApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Activity for displaying the enumerator identification
 */
public class EnumeratorIDActivity extends AppCompatActivity{
    //base url for the api
    public static String  BASE_URL = "http://10.0.2.2:8000/api/user-details/";
    String userToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enumerator_id);

        //initialize veiws
        ImageView imgAcc = (ImageView) findViewById(R.id.imgAccount);
        final TextView enumID = (TextView) findViewById(R.id.enumID);
        final TextView tvEnumFName = (TextView) findViewById(R.id.tvEnumFName);
        final TextView tvEnumLName = (TextView) findViewById(R.id.tvEnumLName);
        final TextView tvCounty = (TextView) findViewById(R.id.tvCounty);
        final TextView tvPhone = (TextView) findViewById(R.id.tvPhone);
        final TextView tvReportsTo = (TextView) findViewById(R.id.tvReportsTo);
        final TextView tvSupervisorNo = (TextView) findViewById(R.id.tvSupervisorNo);


       OkHttpClient.Builder client = new OkHttpClient.Builder();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client.addInterceptor(loggingInterceptor);

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //.client(client.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiService restAPI = retrofit.create(ApiService.class);

        TokenManagement tknMgmt = new TokenManagement(this);
        userToken = tknMgmt.getToken();

        Call<AccountModel> call = restAPI.getUserDetails(LoginActivity.USER_EMAIL,userToken);
        call.enqueue(new Callback<AccountModel>() {
            @Override
            public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                int code = response.code();
                Log.i("RESPONSE CODE","server response code"+code);

                if(response.isSuccessful()){
                    AccountModel userDetails = response.body();

                    Log.i("in on Response","First Name"+userDetails.getFirstName());
                    enumID.setText(userDetails.getId().toString());
                    tvEnumFName.setText(userDetails.getFirstName());
                    tvEnumLName.setText(userDetails.getLastName());
                    tvCounty.setText(userDetails.getCounty());
                    tvPhone.setText(userDetails.getPhoneNumber());
                    tvSupervisorNo.setText(userDetails.getSupervisorPhone());
                    tvReportsTo.setText(userDetails.getReportsTo());
                }
            }

            @Override
            public void onFailure(Call<AccountModel> call, Throwable t) {
                Log.e("in onFailure", "stack");
                t.printStackTrace();
            }
        });
    }

}
