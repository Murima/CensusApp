package com.androidadvance.androidsurvey;

import android.util.Log;

/**
 * Handles looping the forms based on number of occupants
 */

public class LoopCounter {


    public int number;
    public int numberLeft;
    public static boolean isCounterSet;
    public static int counterNumber;
    public static int houseNo;
    public static int headID;
    public static String houseEstate;

    /**
     * set the number of times to loop
     * @param
     */

    public void setNumber(int number){
        isCounterSet=true;
        counterNumber= number;
    }

    public void setHouseNo(int number){
        houseNo= number;
    }

    public void setHeadId(int number){
        headID= number;
    }

    public void setHouseEstate(String estate){
        houseEstate= estate;
    }
    /**
     * get the number of times left
     */
    public static int getNumber(){
        Log.d("DEBUG_LOOP", "in getNumber status="+isCounterSet);

        if (counterNumber!=0){
            counterNumber =counterNumber-1;
            return counterNumber;
        }
        isCounterSet=false;
        return 0;
    }

    public static int getHouseNo(){
        return houseNo;
    }

    public static int getHeadID(){
        return headID;
    }

    public static String  getHouseEstate(){
        return houseEstate;
    }
    /**
     * check counter status
     * @return isCounterSet
     */
    public static boolean counterStatus(){
        return isCounterSet;
    }
}

