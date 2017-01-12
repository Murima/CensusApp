package com.example.knbs.censusapp.api;


import com.example.knbs.censusapp.EnumeratorIDActivity;
import com.example.knbs.censusapp.LoginActivity;
import com.example.knbs.censusapp.POJO.AccountModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * interface that will get data from server
 */
//http://localhost:8000/api/user-details/enumerator@gmail.com/?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjEsImlzcyI6Imh0dHA6XC9cL2xvY2FsaG9zdDo4MDAwXC9hcGlcL2F1dGhlbnRpY2F0ZSIsImlhdCI6MTQ4NDIxOTE0OCwiZXhwIjoxNDg0MjIyNzQ4LCJuYmYiOjE0ODQyMTkxNDgsImp0aSI6IjdiNzVkMWRiMDkyM2ZlY2RkMzJhZDY5MTY0MTE5ODRiIn0.vh73ovFWyGwTpx26NJMqj92kAzCwFIkuactqg7gJuTE
public interface ApiService {


    @GET("{email}/")
    Call<AccountModel> getUserDetails(@Path("email") String email, @Query("token") String token);
}
