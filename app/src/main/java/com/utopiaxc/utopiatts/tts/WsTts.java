package com.utopiaxc.utopiatts.tts;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class WsTts implements Tts{
    private static final String TAG = "WsTts";
    private static volatile Tts mInstance;
    SharedPreferences mSharedPreferences;

    public WsTts(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        initTts();
    }

    public static Tts getInstance(Context context) {
        if (mInstance == null) {
            synchronized (Tts.class) {
                if (mInstance == null) {
                    mInstance = new WsTts(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    @Override
    public void doSpeak(String text, int pitch, int rate) {

    }

    @Override
    public void stopSpeak() {

    }

    @Override
    public void initTts() {

    }
}
