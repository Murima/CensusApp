package com.example.knbs.censusapp;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;

import com.example.knbs.censusapp.api.ApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.knbs.censusapp.Constants.BASE_URL;

/**
 * class to post form data to server
 *
 */


public class PostData {


    public static String  SERVICE_URL = BASE_URL;
    private static String POST_TAG="POST_DEBUG";
    String userToken;
    private Context context;

    public PostData(Context fromContext){
        this.context=fromContext;
    }

    public void postJson(String data){
        /**
         * post the json using retrofit 2
         * @params json string data
         */

        OkHttpClient.Builder client = new OkHttpClient.Builder();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client.addInterceptor(loggingInterceptor);

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVICE_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiService restAPIService = retrofit.create(ApiService.class);

        TokenManagement tknMgmt = new TokenManagement(context);
        userToken = tknMgmt.getToken();

        Map<String, Object> formData = new ArrayMap<>();
        formData.put("form", data);

        RequestBody body = RequestBody.create(okhttp3
                .MediaType.parse("application/json; charset=utf-8"),(new JSONObject(formData))
                .toString());

//serviceCaller is the interface initialized with retrofit.create...
        String email = LoginActivity.USER_EMAIL;
        Call<ResponseBody> call = restAPIService.postFormData(email, userToken, body);

        call.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse)
            {
                try
                {
                    //get your response....
                    Log.d(POST_TAG, "RetroFit2.0 :RetroGetLogin: " + rawResponse.body().string());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable)
            {
                // other stuff...
                Log.d(POST_TAG, "in OnFailure"+throwable);
            }
        });
    }
}
