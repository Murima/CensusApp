package com.example.knbs.censusapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

/**
 * Created by killer on 1/13/17.
 */

public class AddressResultReceiver extends ResultReceiver{

    /**
     * Create a new ResultReceive to receive results.  Your
     * {@link #onReceiveResult} method will be called from the thread running
     * <var>handler</var> if given, or from an arbitrary thread if null.
     *
     * @param handler
     */
    private static String PACKAGE_NAME = "com.example.knbs.censusapp";
    private static String RESULT_DATA_KEY = PACKAGE_NAME +".RESULT_DATA_KEY";
    private static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;
    private static String ADDRESS_TAG="DEBUG_ADDRESS_RECEIVED";

    public static String  addressOutput;

    private Handler handler;

    public AddressResultReceiver(Handler handler) {
        //TODO get more precise location.
        super(handler);
        this.handler = handler;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        // Display the address string stored in resultData
        // or an error message sent from the intent service.
        addressOutput = resultData.getString(RESULT_DATA_KEY);

        if (resultCode == SUCCESS_RESULT) {
            Log.d(ADDRESS_TAG, "in onReceiveResult result is:"+addressOutput);
        }

    }

}
