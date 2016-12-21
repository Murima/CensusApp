package com.example.knbs.censusapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class TokenManagement {

    private static final String TOKEN_PREFS = "TOKEN_PREFS";
    private static final String AUTH_TOKEN = "authToken";
	/*	class to manage tokens from the user
	 */

    private String token;
    private Context context;
    protected TokenManagement(Context userContext) {

        token=null;
        context=userContext;

    }

    protected void storeToken(String userToken){
        SharedPreferences userPrefs = context.getSharedPreferences(TOKEN_PREFS, 0);
        Editor editor = userPrefs.edit();
        editor.putString(AUTH_TOKEN, userToken);
        editor.apply();


    }

    protected String getToken(){
        SharedPreferences prefsFile = context.getSharedPreferences(TOKEN_PREFS, 0);
        String retrievedToken = prefsFile.getString(AUTH_TOKEN, null);

        return retrievedToken;
    }

    protected boolean isTokenStored(){

        return false;
    }
}
