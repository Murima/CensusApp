package com.androidadvance.androidsurvey.fragment;

import android.app.Service;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
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

public class FragmentNumber extends Fragment {

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
        editText_answer.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                boolean isCounterSet = LoopCounter.counterStatus();

                if (description.equals("occupant_No") && !isCounterSet){
                    String number = editText_answer.getText().toString().trim();
                    int counter = Integer.parseInt(number);
                    loopCounter.setNumber(counter);
                    Answers.getInstance().put_answer(description, number);

                }
                else if(description.equals("occupant_No") && isCounterSet){
                    int counter = LoopCounter.getNumber();
                    String counterString = Integer.toString(counter);
                    Answers.getInstance().put_answer(description, counterString);

                }

                if (description.equals("head_id") && !isCounterSet){
                    String number = editText_answer.getText().toString().trim();
                    int counter = Integer.parseInt(number);
                    loopCounter.setHeadId(counter);
                    Answers.getInstance().put_answer(description, number);

                }

                else if(description.equals("head_id") && isCounterSet){
                    int counter = LoopCounter.getHeadID();
                    String counterString = Integer.toString(counter);
                    Answers.getInstance().put_answer(description, counterString);

                }

                if (description.equals("houseNo") && !isCounterSet){
                    String number = editText_answer.getText().toString().trim();
                    int counter = Integer.parseInt(number);
                    loopCounter.setHouseNo(counter);
                    Answers.getInstance().put_answer(description, number);

                }

                else if(description.equals("houseNo") && isCounterSet){
                    int counter = LoopCounter.getHouseNo();
                    String counterString = Integer.toString(counter);
                    Answers.getInstance().put_answer(description, counterString);
                }

                else {
                    Answers.getInstance().put_answer(description, editText_answer.getText().toString().trim());
                }

                ((SurveyActivity) mContext).go_to_next();
            }
        });

        return rootView;
    }


    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        Question q_data = (Question) getArguments().getSerializable("data");
        description=q_data.getDescription();

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
        boolean trueOccNo = description.equals("occupant_No");
        boolean trueheadID = description.equals("head_id");
        boolean truehouseNo= description.equals("houseNo");

        if (LoopCounter.counterStatus()){
            editText_answer.setEnabled(false);
            editText_answer.setTextColor(Color.RED);
            if (trueheadID){
                int counter=LoopCounter.getHeadID();
                String counterString = Integer.toString(counter);

                editText_answer.setText(counterString);
            }
            if (truehouseNo){
                int counter=LoopCounter.getHouseNo();
                String counterString = Integer.toString(counter);

                editText_answer.setText(counterString);
            }
            if (trueOccNo){
                int counter=LoopCounter.getNumber();
                String counterString = Integer.toString(counter);

                editText_answer.setText(counterString);
            }
            else{

                editText_answer.setEnabled(true);
                editText_answer.requestFocus();
            }

        }
        else {
            editText_answer.setEnabled(true);
            editText_answer.requestFocus();

        }
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText_answer, 0);
    }

    private boolean validator(Editable s) {
        FormValidators inputValidator = new FormValidators(s);

        switch (description){
            case "head_id":
                return inputValidator.lengthValidator();
            case "houseNo":
                return true;
        }
        return  true;
    }


}