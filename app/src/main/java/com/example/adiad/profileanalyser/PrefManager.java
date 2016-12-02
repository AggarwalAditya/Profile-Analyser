package com.example.adiad.profileanalyser;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lincoln on 05/05/16.
 */
public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "ProfileAnalyser";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String EVENT_NAME="event_name";
    private static final String PERSON_NAME="person_name";
    private static final String PHONE_NUMBER="phone_number";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }//true tha

    public void set_event_name(String event_name){
        editor.putString(EVENT_NAME,event_name);
        editor.commit();
    }

    public String get_event_name()
    {
        return pref.getString(EVENT_NAME,"null");
    }

    public void set_person_name(String person_name){
        editor.putString(PERSON_NAME,person_name);
        editor.commit();
    }

    public void set_phone_number(String phone_number){
        editor.putString(PHONE_NUMBER,phone_number);
        editor.commit();
    }

    public String get_person_name()
    {
        return pref.getString(PERSON_NAME,"null");
    }

    public String get_phone_number()
    {
        return pref.getString(PHONE_NUMBER,"null");
    }
}