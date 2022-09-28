package com.utopiaxc.utopiatts.tts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.microsoft.cognitiveservices.speech.CancellationReason;
import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisCancellationDetails;
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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MsTts implements Tts{
    private static final String TAG = "MsTts";
    private static volatile Tts mInstance;
    private SpeechSynthesizer mSpeechSynthesizer;
    SharedPreferences mSharedPreferences;

    public MsTts(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        initTts();
    }

    public static Tts getInstance(Context context) {
        if (mInstance == null) {
            synchronized (MsTts.class) {
                if (mInstance == null) {
                    mInstance = new MsTts(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    @Override
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
        try {
            SpeechSynthesisResult speechRecognitionResult =
                    mSpeechSynthesizer.SpeakSsmlAsync(ssml.toString()).get();
            if (speechRecognitionResult.getReason() == ResultReason.SynthesizingAudioCompleted) {
                Log.d(TAG,"Speech synthesized to speaker for text = " + text);
            } else if (speechRecognitionResult.getReason() == ResultReason.Canceled) {
                SpeechSynthesisCancellationDetails cancellation =
                        SpeechSynthesisCancellationDetails.fromResult(speechRecognitionResult);
                if (cancellation.getReason() == CancellationReason.Error) {
                    Log.e(TAG,"Speech synthesized error");
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopSpeak() {
        if (mSpeechSynthesizer != null) {
            mSpeechSynthesizer.StopSpeakingAsync();
        }
    }

    @Override
    public void initTts() {
        Log.i(TAG, "initTts");
        Regions region = Regions.getRegion(
                mSharedPreferences.getString(
                        SettingsEnum.AZURE_REGION.getKey(),
                        (String) SettingsEnum.AZURE_REGION.getDefaultValue()));
        String token=mSharedPreferences.getString(
                SettingsEnum.AZURE_TOKEN.getKey(),
                (String) SettingsEnum.AZURE_TOKEN.getDefaultValue());
        if ("".equals(token)){
            token=(String) SettingsEnum.AZURE_TOKEN.getDefaultValue();
        }
        SpeechConfig speechConfig = SpeechConfig.fromSubscription(token, region.getId());
        OutputFormat outputFormat = OutputFormat.getOutputFormat(
                mSharedPreferences.getString(
                        SettingsEnum.OUTPUT_FORMAT.getKey(),
                        (String) SettingsEnum.OUTPUT_FORMAT.getDefaultValue()));
        speechConfig.setSpeechSynthesisOutputFormat(outputFormat.getSpeechSynthesisOutputFormat());
        AudioConfig audioConfig = AudioConfig.fromDefaultSpeakerOutput();
        mSpeechSynthesizer = new SpeechSynthesizer(speechConfig, audioConfig);
    }

    public static boolean testAzureConfig(String token,String region){
        SpeechConfig speechConfig = SpeechConfig.fromSubscription(token, region);
        SpeechSynthesizer speechSynthesizer=new SpeechSynthesizer(speechConfig,null);
        try {
            SpeechSynthesisResult speechRecognitionResult =
                    speechSynthesizer.SpeakTextAsync("").get();
            if (speechRecognitionResult.getReason() == ResultReason.SynthesizingAudioCompleted) {
                return true;
            }
            else if (speechRecognitionResult.getReason() == ResultReason.Canceled) {
                SpeechSynthesisCancellationDetails cancellation =
                        SpeechSynthesisCancellationDetails.fromResult(speechRecognitionResult);
                if (cancellation.getReason() == CancellationReason.Error) {
                    return false;
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
