package com.androidadvance.androidsurvey.fragment;

import android.app.Service;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidadvance.androidsurvey.Answers;
import com.androidadvance.androidsurvey.FormValidators;
import com.androidadvance.androidsurvey.LoopCounter;
import com.androidadvance.androidsurvey.R;
import com.androidadvance.androidsurvey.SurveyActivity;
import com.androidadvance.androidsurvey.models.Question;

public class FragmentTextSimple extends Fragment {

    private FragmentActivity mContext;
    private Button button_continue;
    private TextView textview_q_title;
    private EditText editText_answer;
    private String description;
    LoopCounter loopCounter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_text_simple, container, false);

        loopCounter = new LoopCounter();
        button_continue = (Button) rootView.findViewById(R.id.button_continue);
        textview_q_title = (TextView) rootView.findViewById(R.id.textview_q_title);
        editText_answer = (EditText) rootView.findViewById(R.id.editText_answer);
        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCounterSet = LoopCounter.counterStatus();

                if (description.equals("household_estate") && !isCounterSet){
                    String estate = editText_answer.getText().toString().trim();
                    loopCounter.setHouseEstate(estate);
                    Answers.getInstance().put_answer(description, estate);
                }
                else if(description.equals("household_estate") && isCounterSet){
                    String estate = LoopCounter.getHouseEstate();
                    Answers.getInstance().put_answer(description, estate);

                }
                else {
                    Answers.getInstance().put_answer(description, editText_answer.getText().toString().trim());
                }
                ((SurveyActivity) mContext).go_to_next();
            }
        });


        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContext = getActivity();
        Question q_data = (Question) getArguments().getSerializable("data");
        description= q_data.getDescription();

        if (q_data.getRequired()) {
            button_continue.setVisibility(View.GONE);
            editText_answer.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    boolean valid = validator(s);
                    if (valid) {
                        button_continue.setVisibility(View.VISIBLE);
                    } else {
                        button_continue.setVisibility(View.GONE);
                    }
                }
            });
        }

        textview_q_title.setText(Html.fromHtml(q_data.getQuestionTitle()));
        editText_answer.requestFocus();
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText_answer, 0);


    }

    private boolean validator(Editable s) {
        FormValidators inputValidator = new FormValidators(s);

        switch (description){
            case "date_of_moving_in":
                return inputValidator.dateValidator();

            case "date_last_child":
                return inputValidator.dateValidator();
        }
        return  true;
    }
}