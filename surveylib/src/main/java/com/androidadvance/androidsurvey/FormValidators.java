package com.androidadvance.androidsurvey;

import android.text.Editable;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * validator class for the forms.
 */

public class FormValidators {
    private Editable s;
    public FormValidators(Editable userChar){
        s= userChar;
    }

    public boolean lengthValidator(){
      /*
      check length of ID
       */
        if (s.length()==8){
            if (numberValidator()){
                return true;
            }
            else{
                return  false;
            }

        }
        else {
            return false;
        }

    }
    public boolean numberValidator(){
        /*
        check the charachters are all numbers
         */
        if (TextUtils.isDigitsOnly(s)){
            return true;
        }
        else   {
            return false;
        }
    }
    public boolean dateValidator(){
        /*
        check the correct date format is input
         */

        String dateFormat="dd-MM-yyyy";
        String dateCompare = "01-01-1900";

        //get today's date
        Date currDate = new Date();
        String formattedDate = new SimpleDateFormat(dateFormat).format(currDate);

        if(s== null){
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        try {

            //if not valid, it will throw ParseException
            Date date = sdf.parse(s.toString());
            Date compareDate = sdf.parse(dateCompare);

            Date todaysDate= sdf.parse(formattedDate);

            if (date.before(compareDate)) {
                return false;
            }
            if (date.after(todaysDate)){
                return  false;
            }

            //System.out.println(date);

        } catch (ParseException e) {

            //e.printStackTrace();
            return false;
        }

        return true;
    }

}

