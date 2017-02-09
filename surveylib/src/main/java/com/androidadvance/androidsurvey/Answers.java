package com.androidadvance.androidsurvey;

import android.util.Log;

import com.google.gson.Gson;


import java.util.LinkedHashMap;

//Singleton Answers ........

public class Answers {
    private volatile static Answers uniqueInstance;
    public LinkedHashMap<String, String> answered_hashmap = new LinkedHashMap<>();


    public Answers() {
    }

    public void put_answer(String key, String value) {
        answered_hashmap.put(key, value);
    }

    public void clear_hashmap(){
        /**
         * clears the hashmap before starting a new category.
         */
        Log.d("DEBUG_POST", "in clear_hashmap");
        answered_hashmap.clear();

    }
    public String get_json_object() {
        Gson gson = new Gson();
        return gson.toJson(answered_hashmap,LinkedHashMap.class);
    }

    @Override
    public String toString() {
        return String.valueOf(answered_hashmap);
    }

    public static Answers getInstance() {
        if (uniqueInstance == null) {
            synchronized (Answers.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new Answers();
                }
            }
        }
        return uniqueInstance;
    }
}
