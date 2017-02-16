package com.example.knbs.censusapp;

import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;

import com.androidadvance.androidsurvey.SurveyActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.knbs.censusapp.Constants.SUCCESS_RESULT;
//import java.util.jar.Manifest;

/**
 * Category Activity to choose categories of questions
 */
public class CategoryActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener
        ,AddressResultReceiver.Receiver
{
    private static final int SURVEY_REQUEST = 1337;
    private static String LOCATION_TAG = "LOCATION_DEBUG";
    private static String POST_TAG = "POST_DEBUG";

    GoogleApiClient googleApiClient;

    Location lastLocation;
    LocationRequest locationRequest;
    //address receiver
    public AddressResultReceiver addResultReceiver;
    public static String addressOutput;

    TextView tvForAll, tvFemales13, tvtvDisablepips, tvAbove3yrs,
            tvICTinfo, tvLabourforce, tvOwnershipAmenities, tvHousingCondition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_categories);

        tvForAll = (TextView) findViewById(R.id.tvForAll);
        tvFemales13 = (TextView) findViewById(R.id.tvFemales13);
        tvtvDisablepips = (TextView) findViewById(R.id.tvDisablepips);
        tvAbove3yrs = (TextView) findViewById(R.id.tvAbove3yrs);
        tvICTinfo = (TextView) findViewById(R.id.tvICTinfo);
        tvLabourforce = (TextView) findViewById(R.id.tvLabourforce);
        tvOwnershipAmenities = (TextView) findViewById(R.id.tvOwnershipAmenities);
        tvHousingCondition = (TextView) findViewById(R.id.tvHousingCondition);

        tvForAll.setOnClickListener(this);
        tvFemales13.setOnClickListener(this);
        tvtvDisablepips.setOnClickListener(this);
        tvAbove3yrs.setOnClickListener(this);
        tvICTinfo.setOnClickListener(this);
        tvLabourforce.setOnClickListener(this);
        tvOwnershipAmenities.setOnClickListener(this);
        tvHousingCondition.setOnClickListener(this);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        addResultReceiver = new AddressResultReceiver(new Handler());
        addResultReceiver.setReceiver(this);


    }
    @Override
    protected void onStart(){
        googleApiClient.connect();
        super.onStart();

    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SURVEY_REQUEST) {
            if (resultCode == RESULT_OK) {

                String answers_json = data.getExtras().getString("answers");
                Log.d("****", "****************** WE HAVE ANSWERS ******************");
                Log.v("ANSWERS JSON", answers_json);
                Log.d("****", "*****************************************************");

                PostData postData = new PostData(this);
                postData.postJson(answers_json);

                boolean counterStatus = data.getExtras().getBoolean("counter");
                Log.d(POST_TAG,"in onActivityResult counter status"+counterStatus);
                if (counterStatus){
                    tvForAll.performClick();
                }
            }
        }
    }

    private String loadSurveyJson(String filename) {
        try {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    @Override
    public void onClick(View v) {
        String CATEGORY_ID;
        Date currDate = new Date();
        String formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(currDate);


        switch (v.getId()) {
            case R.id.tvForAll:
                CATEGORY_ID="1";
                Intent i_survey = new Intent(CategoryActivity.this, SurveyActivity.class);
                i_survey.putExtra("json_survey", loadSurveyJson("information_for_all.json"));
                i_survey.putExtra("category", CATEGORY_ID);
                i_survey.putExtra("location", addressOutput);
                i_survey.putExtra("date",  formattedDate);
                startActivityForResult(i_survey, SURVEY_REQUEST);
                break;

            case R.id.tvFemales13:
                CATEGORY_ID="2";
                Intent i_survey2 = new Intent(CategoryActivity.this, SurveyActivity.class);
                i_survey2.putExtra("json_survey", loadSurveyJson("information_for_females_above_12yrs.json"));
                i_survey2.putExtra("category", CATEGORY_ID);
                i_survey2.putExtra("date",  formattedDate);
                i_survey2.putExtra("location", addressOutput);
                startActivityForResult(i_survey2, SURVEY_REQUEST);
                break;

            case R.id.tvAbove3yrs:
                CATEGORY_ID="3";
                Intent i_survey3 = new Intent(CategoryActivity.this, SurveyActivity.class);
                i_survey3.putExtra("json_survey", loadSurveyJson("information_for_persons_above_3yrs.json"));
                i_survey3.putExtra("category", CATEGORY_ID);
                i_survey3.putExtra("date",  formattedDate);
                i_survey3.putExtra("location", addressOutput);
                startActivityForResult(i_survey3, SURVEY_REQUEST);
                break;

            case R.id.tvICTinfo:
                CATEGORY_ID="4";
                Intent i_survey4 = new Intent(CategoryActivity.this, SurveyActivity.class);
                i_survey4.putExtra("json_survey", loadSurveyJson("information_regarding_ICT.json"));
                i_survey4.putExtra("category", CATEGORY_ID);
                i_survey4.putExtra("location", addressOutput);
                i_survey4.putExtra("date",  formattedDate);
                startActivityForResult(i_survey4, SURVEY_REQUEST);
                break;

            case R.id.tvLabourforce:
                CATEGORY_ID="5";
                Intent i_survey5 = new Intent(CategoryActivity.this, SurveyActivity.class);
                i_survey5.putExtra("json_survey", loadSurveyJson("labour_force_particulars_above_5yrs.json"));
                i_survey5.putExtra("category", CATEGORY_ID);
                i_survey5.putExtra("location", addressOutput);
                i_survey5.putExtra("date",  formattedDate);
                startActivityForResult(i_survey5, SURVEY_REQUEST);
                break;

            case R.id.tvOwnershipAmenities:
                CATEGORY_ID="6";
                Intent i_survey6 = new Intent(CategoryActivity.this, SurveyActivity.class);
                i_survey6.putExtra("json_survey", loadSurveyJson("ownership_of_household_assets.json"));
                i_survey6.putExtra("category", CATEGORY_ID);
                i_survey6.putExtra("location", addressOutput);
                i_survey6.putExtra("date",  formattedDate);
                startActivityForResult(i_survey6, SURVEY_REQUEST);
                break;

            case R.id.tvHousingCondition:
                CATEGORY_ID="7";
                Intent i_survey7 = new Intent(CategoryActivity.this, SurveyActivity.class);
                i_survey7.putExtra("json_survey", loadSurveyJson("housing_conditions_and_amenities.json"));
                i_survey7.putExtra("category", CATEGORY_ID);
                i_survey7.putExtra("location", addressOutput);
                i_survey7.putExtra("date",  formattedDate);
                startActivityForResult(i_survey7, SURVEY_REQUEST);
                break;

            case R.id.tvDisablepips:
                CATEGORY_ID="8";
                Intent i_survey8 = new Intent(CategoryActivity.this, SurveyActivity.class);
                i_survey8.putExtra("json_survey", loadSurveyJson("information_for_persons_with_disabilities.json"));
                i_survey8.putExtra("category", CATEGORY_ID);
                i_survey8.putExtra("location", addressOutput);
                i_survey8.putExtra("date",  formattedDate);
                startActivityForResult(i_survey8, SURVEY_REQUEST);
                break;

            default:
                Log.i("DEFAULT ONCLICK OPT","This is default switch mode");
                break;
        }
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        //once connected to the google services
        Log.d(LOCATION_TAG,"connected to google API");

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }

        createLocationRequest();
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (lastLocation!=null)
        {
            Log.i("LOCATION_DEBUG","Latitude is:"+lastLocation.getLatitude());
            Log.i("LOCATION_DEBUG","Longitude is:"+lastLocation.getLongitude());

            // Determine whether a Geocoder is available.
            if (!Geocoder.isPresent())
            {
                Toast.makeText(this, "No geocoder is available",
                        Toast.LENGTH_LONG).show();
                return;
            }

            startIntentService();

        }
        else{
            Log.d("LOCATION_DEBUG","location is null");
            startLocationUpdates();
            // startIntentService();
        }

    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(LOCATION_TAG,"in onConnectionFailed");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(LOCATION_TAG,"in location changed");
        //update the last location

        lastLocation = location;
        startIntentService();

        Toast.makeText(this, "LOcation updated",
                Toast.LENGTH_LONG).show();
    }


    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {

        // Display the address string stored in resultData
        // or an error message sent from the intent service.
        addressOutput = resultData.getString(Constants.RESULT_DATA_KEY);

        if (resultCode == SUCCESS_RESULT) {
            Log.d(AddressResultReceiver.ADDRESS_TAG, "in onReceiveResult result is:"+addressOutput);


        }
    }
    protected void   createLocationRequest() {

        Log.d(LOCATION_TAG, "in createLocationRequest");

        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void locationSettingsRequest(){
        //TODO check location settings from user.

        LocationRequest locationRequest = new LocationRequest();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient,
                        builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {

                final Status status = result.getStatus();
                //final LocationSettingsStates= result.getLocationSettingsStates();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can
                        // initialize location requests here.

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                 /*       try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    OuterClass.this,
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }*/
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.

                        break;
                }
            }
        });

    }

    protected void startLocationUpdates(){
        /**
         * get current location
         */

        Log.d(LOCATION_TAG,"in startLocationUpdates");
        if ( ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            Log.d(LOCATION_TAG,"permission failed");

        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);

    }

    protected void stopLocationUpdates(){
        //TODO Implement stop location update on last form.
    }

    protected void startIntentService() {
        /**
         * start resolving the longitude to an address
         */

        Log.d(LOCATION_TAG,"in startIntentService");
        //addResultReceiver = new AddressResultReceiver(null);

        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, addResultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, lastLocation);
        startService(intent);
    }


}
