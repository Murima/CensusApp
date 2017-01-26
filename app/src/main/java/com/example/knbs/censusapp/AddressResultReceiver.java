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
    public static String ADDRESS_TAG="DEBUG_ADDRESS_RECEIVED";
    private Receiver mReceiver;

    public static String  addressOutput;

    private Handler handler;

    public AddressResultReceiver(Handler handler) {
        //TODO get more precise location.
        super(handler);
        //this.handler = handler;
    }
    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);

    }

    public void setReceiver(Receiver receiver) {
        mReceiver = receiver;
    }
    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }


}
