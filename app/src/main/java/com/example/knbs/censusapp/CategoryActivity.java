package com.example.knbs.censusapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.androidadvance.androidsurvey.SurveyActivity;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;

/**
 * Category Activity to choose categories of questions
 */
public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int SURVEY_REQUEST = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_categories);

        TextView tvForAll = (TextView) findViewById(R.id.tvForAll);
        TextView tvFemales13 = (TextView) findViewById(R.id.tvFemales13);
        TextView tvtvDisablepips = (TextView) findViewById(R.id.tvDisablepips);
        TextView tvAbove3yrs = (TextView) findViewById(R.id.tvAbove3yrs);
        TextView tvICTinfo = (TextView) findViewById(R.id.tvICTinfo);
        TextView tvLabourforce = (TextView) findViewById(R.id.tvLabourforce);
        TextView tvOwnershipAmenities = (TextView) findViewById(R.id.tvOwnershipAmenities);
        TextView tvHousingCondition = (TextView) findViewById(R.id.tvHousingCondition);

        tvForAll.setOnClickListener(this);
        tvFemales13.setOnClickListener(this);
        tvtvDisablepips.setOnClickListener(this);
        tvAbove3yrs.setOnClickListener(this);
        tvICTinfo.setOnClickListener(this);
        tvLabourforce.setOnClickListener(this);
        tvOwnershipAmenities.setOnClickListener(this);
        tvHousingCondition.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SURVEY_REQUEST) {
            if (resultCode == RESULT_OK) {

                String answers_json = data.getExtras().getString("answers");
                Log.d("****", "****************** WE HAVE ANSWERS ******************");
                Log.v("ANSWERS JSON", answers_json);
                Log.d("****", "*****************************************************");

                //do whatever you want with them...
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
        switch (v.getId()) {
            case R.id.tvForAll:
                Intent i_survey = new Intent(CategoryActivity.this, SurveyActivity.class);
                i_survey.putExtra("json_survey", loadSurveyJson("information_for_all.json"));
                startActivityForResult(i_survey, SURVEY_REQUEST);
                break;

            case R.id.tvFemales13:
                Intent i_survey2 = new Intent(CategoryActivity.this, SurveyActivity.class);
                i_survey2.putExtra("json_survey", loadSurveyJson("information_for_females_above_12yrs.json"));
                startActivityForResult(i_survey2, SURVEY_REQUEST);
                break;

            case R.id.tvAbove3yrs:
                Intent i_survey3 = new Intent(CategoryActivity.this, SurveyActivity.class);
                i_survey3.putExtra("json_survey", loadSurveyJson("information_for_persons_above_3yrs.json"));
                startActivityForResult(i_survey3, SURVEY_REQUEST);
                break;

            case R.id.tvICTinfo:
                Intent i_survey4 = new Intent(CategoryActivity.this, SurveyActivity.class);
                i_survey4.putExtra("json_survey", loadSurveyJson("information_regarding_ICT.json"));
                startActivityForResult(i_survey4, SURVEY_REQUEST);
                break;

            case R.id.tvLabourforce:
                Intent i_survey5 = new Intent(CategoryActivity.this, SurveyActivity.class);
                i_survey5.putExtra("json_survey", loadSurveyJson("labour_force_particulars_above_5yrs.json"));
                startActivityForResult(i_survey5, SURVEY_REQUEST);
                break;

            case R.id.tvOwnershipAmenities:
                Intent i_survey6 = new Intent(CategoryActivity.this, SurveyActivity.class);
                i_survey6.putExtra("json_survey", loadSurveyJson("ownership_of_household_assets.json"));
                startActivityForResult(i_survey6, SURVEY_REQUEST);
                break;

            case R.id.tvHousingCondition:
                Intent i_survey7 = new Intent(CategoryActivity.this, SurveyActivity.class);
                i_survey7.putExtra("json_survey", loadSurveyJson("housing_conditions_and_amenities.json"));
                startActivityForResult(i_survey7, SURVEY_REQUEST);
                break;

            case R.id.tvDisablepips:
                Intent i_survey8 = new Intent(CategoryActivity.this, SurveyActivity.class);
                i_survey8.putExtra("json_survey", loadSurveyJson("information_for_persons_with_disabilities.json"));
                startActivityForResult(i_survey8, SURVEY_REQUEST);
                break;

            default:
                Log.i("DEFAULT ONCLICK OPT","This is default switch mode");
                break;
        }
    }


}

