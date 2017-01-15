package com.example.knbs.censusapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;


/**
 * login activity that checks credentials and logs the enumerator from the server
 */

public class LoginActivity extends AppCompatActivity {
    protected Context context;
    protected EditText etEmail;
    protected EditText etPassword;
    private UserLoginTask mAuthTask=null;
    protected Button btLogin;
    public static String USER_EMAIL;
    public static boolean TOKEN_AVAILABLE=false;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //setup login forms
        etEmail = (EditText) findViewById(R.id.input_email);
        etPassword = (EditText) findViewById(R.id.input_password);
        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                int customImeActionid = getResources().getInteger(R.integer.customImeActionId);
                if(actionId==customImeActionid || actionId==EditorInfo.IME_NULL){
                    attemptLogin();

                }
                return false;
            }
        });
        btLogin =(Button) findViewById(R.id.btn_login);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        //TODO implement on back presesd logic properly
        super.onBackPressed();

    }

    public void attemptLogin() {
        // attempt to login the enumerator


        //reset errors
        etEmail.setError(null);
        etPassword.setError(null);
        String emailEntered = etEmail.getText().toString();

        //store email for other classes to use
        USER_EMAIL = emailEntered;

        String passwordEntered = etPassword.getText().toString();

        validateCredentials(emailEntered, passwordEntered);

        mAuthTask = new UserLoginTask(this, emailEntered, passwordEntered);
        mAuthTask.execute();

        Log.i("IN ATTEMPT LOGIN", "token_available status"+TOKEN_AVAILABLE);


    }

    private void validateCredentials(String emailEntered, String passwordEntered) {
        if(mAuthTask != null){
            return;
        }

        boolean cancel = false;
        View focusView = null;

        //check valid email address and password
        if(TextUtils.isEmpty(emailEntered)){
            etEmail.setError(getString(R.string.error_field_required));
            focusView=etEmail;
            cancel=true;

        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailEntered).matches()){
            etEmail.setError(getString(R.string.error_invalid_email));
            focusView=etEmail;
            cancel=true;
        }

        if (TextUtils.isEmpty(passwordEntered)) {
            etPassword.setError(getString(R.string.error_field_required));
            focusView=etPassword;
            cancel=true;

        }

        else if(passwordEntered.length()<=4){
            etPassword.setError(getString(R.string.error_invalid_password));
            focusView=etPassword;
            cancel=true;
        }

        if (cancel) {
            //there was an error dont attempt to log in
            focusView.requestFocus();

        }

        else {
            //everythings ok login
            return;
        }
    }

    protected void onLoginSuccess() {
        String text = "Login Success Welcome!";
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        Intent activityHome = new Intent(this, EnumeratorHomeActivity.class);
        startActivity(activityHome);
    }

    protected void onLoginFailure(){
        View focusView = null;
        String text="Login Failed Please Consult your Supervisor for credentials";
        Context context = getApplicationContext();

        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        focusView=etEmail;
        focusView.requestFocus();
        btLogin.setClickable(true);
        etPassword.setError(getString(R.string.error_incorrect_password));
        etEmail.setError(getString(R.string.error_email_incorrect));
    }


    public class UserLoginTask extends AsyncTask<Void, Void, Void> {
        //class to log in the Enumerators via tokens

        private static final String API_AUTH = "http://10.0.2.2:8000/api/authenticate?";
        private String email;
        private String password;
        StringBuilder resultBuilder;
        ProgressDialog progressDiag;
        public LoginActivity activityContext;


        protected UserLoginTask(LoginActivity originContext, String userEmail, String userPassword) {
            // initialize parameters
            email=userEmail;
            password=userPassword;
            this.activityContext = originContext;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            btLogin.setClickable(false);
            progressDiag = new ProgressDialog(activityContext, R.style.AppTheme_Dark_Dialog);
            progressDiag.setIndeterminate(false);
            progressDiag.setMessage("Authenticating...");
            progressDiag.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            if (isNetworkAvailable()) {
                try {
                    URL url = new URL(API_AUTH);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");


                    Uri.Builder builder = Uri.parse(API_AUTH).buildUpon()
                            .appendQueryParameter("email", email)
                            .appendQueryParameter("password", password);

                    String queryParams = builder.build().getEncodedQuery();

                    OutputStream outStream = connection.getOutputStream();//outputstream  for writing
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    //write the params
                    writer.write(queryParams);
                    //bufferedwriter safety operations
                    writer.flush();
                    writer.close();

                    outStream.close();

                    connection.connect();

                    //Log.i("GET ERROR STREAM ",""+errorstream);

                    InputStream byteStream = connection.getInputStream(); //inputstream for reading
                    BufferedReader buffferRD = new BufferedReader(new InputStreamReader(byteStream));

                    String line;
                    while ((line = buffferRD.readLine()) != null) {
                        resultBuilder = new StringBuilder();
                        resultBuilder.append(line);
                    }
                    String token = resultBuilder.toString();
                    storeToken(token);

                    Log.i("RESULTING TOKEN",""+resultBuilder.toString());


                } catch (MalformedURLException e) {
                    Log.e("MalformedURLException", "malformed URL");
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.e("IOException", "ERROR IN I/O :EXCEPTION BLOCK");
                    e.printStackTrace();
                    TOKEN_AVAILABLE=false;
                    String token = null;
                    storeToken(token);

                }
            }

            else {
                Log.i("NO INTERNET CONNECTION","exception");
            }


            return null;
        }

        private void storeToken(String tokenString) {
            //store the token retrieved
            String tokenKey="token";
            String tokenValue="";

            try {
                JSONObject jsonObject = new JSONObject(tokenString);
                if(jsonObject.has(tokenKey)){
                    TOKEN_AVAILABLE=true;
                    tokenValue = jsonObject.getString(tokenKey);

                }

            }
            catch (Exception e){
                e.printStackTrace();
            }
            TokenManagement tokenMgmt = new TokenManagement(activityContext);
            tokenMgmt.storeToken(tokenValue);
        }

        public boolean isNetworkAvailable() {
            //check if network is available

            ConnectivityManager cm = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            //if networkInfo is not null
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDiag.setMessage("Done!");
            progressDiag.dismiss();

            if (TOKEN_AVAILABLE) {
                onLoginSuccess();

            } else {
                onLoginFailure();
            }
        }
    }
}