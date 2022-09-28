package com.utopiaxc.utopiatts.tts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisResult;
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import com.utopiaxc.utopiatts.enums.SettingsEnum;
import com.utopiaxc.utopiatts.tts.enums.Actors;
import com.utopiaxc.utopiatts.tts.enums.OutputFormat;
import com.utopiaxc.utopiatts.tts.enums.Regions;
import com.utopiaxc.utopiatts.tts.enums.Roles;
import com.utopiaxc.utopiatts.tts.enums.Styles;
import com.utopiaxc.utopiatts.tts.utils.Ssml;

import java.util.concurrent.Future;

public class MsTts {
    private static final String TAG = "MsTts";
    private static volatile MsTts mInstance;
    private SpeechSynthesizer mSpeechSynthesizer;
    SharedPreferences mSharedPreferences;

    public MsTts(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        initTts();
    }

    public static MsTts getInstance(Context context) {
        if (mInstance == null) {
            synchronized (MsTts.class) {
                if (mInstance == null) {
                    mInstance = new MsTts(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    public void doSpeak(String text, int pitch, int rate) {
        Actors actor = Actors.getActor(
                mSharedPreferences.getString(SettingsEnum.ACTOR.getKey(),
                        (String) SettingsEnum.ACTOR.getDefaultValue()));
        Roles role = Roles.getRole(
                mSharedPreferences.getString(SettingsEnum.ROLE.getKey(),
                        (String) SettingsEnum.ROLE.getDefaultValue()));
        Styles style = Styles.getStyle(
                mSharedPreferences.getString(SettingsEnum.ROLE.getKey(),
                        (String) SettingsEnum.ROLE.getDefaultValue()));
        int styleDegree = mSharedPreferences.getInt(SettingsEnum.STYLE_DEGREE.getKey(),
                (Integer) SettingsEnum.STYLE_DEGREE.getDefaultValue());
        Ssml ssml = new Ssml(text, actor.getId(), pitch,
                rate, role.getId(), style.getId(), styleDegree);
        Future<SpeechSynthesisResult> speechSynthesisResultFuture =
                mSpeechSynthesizer.SpeakSsmlAsync(ssml.toString());
        while (!speechSynthesisResultFuture.isDone()) {
            if (speechSynthesisResultFuture.isCancelled()) {
                Log.d(TAG, "speechSynthesisResultFuture.isCancelled");
                break;
            }
        }
    }

    public void stopSpeak() {
        if (mSpeechSynthesizer != null) {
            mSpeechSynthesizer.StopSpeakingAsync();
        }
    }

    public SpeechSynthesizer getSpeechSynthesizer() {
        return mSpeechSynthesizer;
    }

    public void initTts() {
        Log.i(TAG, "initTts");
        Regions region = Regions.getRegion(
                mSharedPreferences.getString(
                        SettingsEnum.AZURE_REGION.getKey(),
                        (String) SettingsEnum.AZURE_REGION.getDefaultValue()));
        SpeechConfig mSpeechConfig = SpeechConfig.fromSubscription(
                mSharedPreferences.getString(
                        SettingsEnum.AZURE_TOKEN.getKey(),
                        (String) SettingsEnum.AZURE_TOKEN.getDefaultValue()), region.getId());
        OutputFormat outputFormat = OutputFormat.getOutputFormat(
                mSharedPreferences.getString(
                        SettingsEnum.OUTPUT_FORMAT.getKey(),
                        (String) SettingsEnum.OUTPUT_FORMAT.getDefaultValue()));
        mSpeechConfig.setSpeechSynthesisOutputFormat(outputFormat.getSpeechSynthesisOutputFormat());
        AudioConfig audioConfig = AudioConfig.fromDefaultSpeakerOutput();
        mSpeechSynthesizer = new SpeechSynthesizer(mSpeechConfig, audioConfig);
    }
}
