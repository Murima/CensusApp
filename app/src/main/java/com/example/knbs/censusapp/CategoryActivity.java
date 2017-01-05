package com.example.knbs.censusapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.androidadvance.androidsurvey.SurveyActivity;

import java.io.IOException;
import java.io.InputStream;

/**
 * Category Activity to choose categories of questions
 */
public class CategoryActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int SURVEY_REQUEST = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_categories);

        TextView tvForAll = (TextView) findViewById(R.id.tvForAll);
        tvForAll.setOnClickListener(this);

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
            /*case R.id.tvEnumID:
                Intent enumeratorID = new Intent(this, EnumeratorIDActivity.class);
                startActivity(enumeratorID);
                break;
            default:
                Log.i("DEFAULT ONCLICK OPT","This is default switch mode");
                break;*/
        }
    }
}

