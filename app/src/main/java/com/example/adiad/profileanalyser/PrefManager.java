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
    private static final String HEALTH_MODULE="health_module";
    private static final String SOCIAL_MEDIA_POSTS="social_media_posts";
    private  static final String SOCIAL_MEDIA_LIKES="social_media_likes";
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

    public void set_health_module(String health_info)
    {
        editor.putString(HEALTH_MODULE,health_info);
        editor.commit();
    }

    public void set_social_media_posts(String social_media_posts_info)
    {
        editor.putString(SOCIAL_MEDIA_POSTS,social_media_posts_info);
        editor.commit();
    }

    public String  get_social_media_posts()
    {
        return pref.getString(SOCIAL_MEDIA_POSTS,"null");
    }

    public String get_health_module(){return pref.getString(HEALTH_MODULE,"null");} //health index + heart age

    public String get_person_name()
    {
        return pref.getString(PERSON_NAME,"null");
    }

    public String get_phone_number()
    {
        return pref.getString(PHONE_NUMBER,"null");
    }

    public void set_likes_info(String likes_info)
    {
        editor.putString(SOCIAL_MEDIA_LIKES,likes_info);
        editor.commit();
    }

    public String get_social_media_likes()
    {
        return pref.getString(SOCIAL_MEDIA_LIKES,"null");//penterainment + " " + ptech + " " + psports + " " + pmusic + " "+ppeople+ " " + pnews+ " "+ pecomm+" "+peducation + " "+ phealth +" " + pothers
    }
}
