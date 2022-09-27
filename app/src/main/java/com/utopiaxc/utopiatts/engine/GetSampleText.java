package com.utopiaxc.utopiatts.engine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class GetSampleText extends Activity {
    private static final String TAG = "GetSampleText";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        int result = TextToSpeech.LANG_AVAILABLE;
        Intent returnData = new Intent();

        Intent i = getIntent();
        String language = i.getExtras().getString("language");
        String country = i.getExtras().getString("country");
        String variant = i.getExtras().getString("variant");
        Log.i(TAG, language + "_" + country + "_" + variant);
        Locale locale = new Locale(language, country);
        returnData.putExtra("sampleText", "这是一个语音合成的范例");
        setResult(result, returnData);
        finish();
    }
}
