package com.mandsti.amirul.braintraining;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;




public class ScoreStorage {

    static Context context;
    static SharedPreferences pref;

    private static final ScoreStorage ourInstance = new ScoreStorage();

    public static ScoreStorage getInstance(Context context) {
        pref=context.getSharedPreferences("MyPref", MODE_PRIVATE);
        context = context;
        return ourInstance;
    }

    private ScoreStorage() {

    }


    public  void saveHighScore(int value) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("high_score", value);
        editor.commit();
    }


    public  int getValue(){
        SharedPreferences.Editor editor = pref.edit();
        return pref.getInt("high_score", 0);
    }

    public void remove(){
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("high_score");
        editor.commit();
    }

    public int getBeforeScore(){
        SharedPreferences.Editor editor = pref.edit();
        return pref.getInt("your", 0);

    }




}
