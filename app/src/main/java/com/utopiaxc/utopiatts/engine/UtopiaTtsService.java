package com.utopiaxc.utopiatts.engine;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.speech.tts.SynthesisCallback;
import android.speech.tts.SynthesisRequest;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeechService;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.utopiaxc.utopiatts.enums.SettingsEnum;
import com.utopiaxc.utopiatts.tts.MsTts;
import com.utopiaxc.utopiatts.tts.Tts;
import com.utopiaxc.utopiatts.tts.WsTts;
import com.utopiaxc.utopiatts.tts.enums.Driver;
import com.utopiaxc.utopiatts.tts.enums.OutputFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class UtopiaTtsService extends TextToSpeechService {
    private static final String TAG = "UtopiaTtsService";
    private volatile String[] mCurrentLanguage = null;
    NotificationManager notificationManager;
    Notification.Builder notificationBuilder;
    final String notificationChannelId = UtopiaTtsService.class.getName();
    final String notificationName = "Utopia TTS Service Notification";
    private static final int NOTIFICATION_ID = 1;
    private static final String ACTION_STOP_SERVICE = "action_stop_service";
    private Tts mTts;
    private Thread mSynthesizeTextThread;

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
        super.onCreate();
        if (PreferenceManager.getDefaultSharedPreferences(this)
                .getString(SettingsEnum.TTS_DRIVER.getKey(), Driver.AZURE_SDK.getId())
                .equals(Driver.AZURE_SDK.getId())) {
            mTts = MsTts.getInstance(getApplicationContext());
        } else {
            mTts = WsTts.getInstance(getApplicationContext());
        }
        startForegroundService();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        mTts.stopSpeak();
        if (mSynthesizeTextThread != null) {
            mSynthesizeTextThread.interrupt();
        }
        super.onDestroy();
    }

    @Override
    protected int onIsLanguageAvailable(String lang, String country, String variant) {
        if ((Locale.SIMPLIFIED_CHINESE.getISO3Language().equals(lang)) ||
                (Locale.US.getISO3Language().equals(lang))) {
            if ((Locale.SIMPLIFIED_CHINESE.getISO3Country().equals(country)) ||
                    (Locale.US.getISO3Country().equals(country))) {
                return TextToSpeech.LANG_COUNTRY_AVAILABLE;
            }
            return TextToSpeech.LANG_AVAILABLE;
        }
        return TextToSpeech.LANG_NOT_SUPPORTED;
    }

    @Override
    protected String[] onGetLanguage() {
        return mCurrentLanguage;
    }

    @Override
    protected int onLoadLanguage(String lang, String country, String variant) {
        lang = lang == null ? "" : lang;
        country = country == null ? "" : country;
        variant = variant == null ? "" : variant;
        int result = onIsLanguageAvailable(lang, country, variant);
        if (result == TextToSpeech.LANG_COUNTRY_AVAILABLE ||
                TextToSpeech.LANG_AVAILABLE == result ||
                result == TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE) {
            mCurrentLanguage = new String[]{lang, country, variant};
        }
        return result;
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop");
        mTts.stopSpeak();
        if (mSynthesizeTextThread != null) {
            mSynthesizeTextThread.interrupt();
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onSynthesizeText(SynthesisRequest synthesisRequest,
                                    SynthesisCallback synthesisCallback) {
        Log.i(TAG, "onSynthesizeText");
        int load = onLoadLanguage(synthesisRequest.getLanguage(), synthesisRequest.getCountry(),
                synthesisRequest.getVariant());
        if (load == TextToSpeech.LANG_NOT_SUPPORTED) {
            synthesisCallback.error();
            Log.i(TAG, "LANG_NOT_SUPPORTED");
            return;
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        OutputFormat outputFormat = OutputFormat.getOutputFormat(
                preferences.getString(SettingsEnum.OUTPUT_FORMAT.getKey(),
                        (String) SettingsEnum.OUTPUT_FORMAT.getDefaultValue()));
        synthesisCallback.start(outputFormat.getSoundFrequency(),
                outputFormat.getAudioFormat(), 1);
        mSynthesizeTextThread = new Thread(new SynthesizeText(synthesisRequest, synthesisCallback));
        mSynthesizeTextThread.start();
        try {
            mSynthesizeTextThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class SynthesizeText implements Runnable {
        SynthesisRequest synthesisRequest;
        SynthesisCallback synthesisCallback;

        public SynthesizeText(SynthesisRequest synthesisRequest,
                              SynthesisCallback synthesisCallback) {
            this.synthesisRequest = synthesisRequest;
            this.synthesisCallback = synthesisCallback;
        }

        @Override
        public void run() {
            String textToSpeech = synthesisRequest.getCharSequenceText().toString();
            List<String> text = new ArrayList<>();
            StringBuilder textBuilder = new StringBuilder();
            for (int i = 0; i < textToSpeech.length(); i++) {
                char c = textToSpeech.charAt(i);
                if (".。！!?？".contains(String.valueOf(c))) {
                    textBuilder.append(c);
                    if (textBuilder.length() >= 50) {
                        text.add(textBuilder.toString());
                        textBuilder.setLength(0);
                    }
                } else {
                    textBuilder.append(c);
                }
            }
            text.add(textBuilder.toString());
            for (String s : text) {
                Log.i(TAG, s);
                if (!mTts.doSpeak(s,
                        synthesisRequest.getPitch(), synthesisRequest.getSpeechRate(),
                        synthesisCallback)) {
                    synthesisCallback.error();
                    return;
                }
            }
            synthesisCallback.done();
        }
    }

    private void startForegroundService() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(notificationChannelId,
                    notificationName, NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(notificationChannel);

        }
        startForeground(NOTIFICATION_ID, getNotification());
    }

    private Notification getNotification() {
        Intent stopSelf = new Intent(this, UtopiaTtsService.class);
        stopSelf.setAction(ACTION_STOP_SERVICE);
        PendingIntent pStopSelf = PendingIntent.getService(this, 0, stopSelf,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        notificationBuilder = new Notification.Builder(this)
                .setOnlyAlertOnce(true)
                .setVibrate(null)
                .setSound(null)
                .setLights(0, 0, 0)
                .setContentTitle("Utopia TTS Service")
                .setContentText("UtopiaTTS Service is running...");

        Notification.Action action;
        action = new Notification.Action.Builder(null, "stop", pStopSelf).build();
        notificationBuilder.addAction(action);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder.setChannelId(notificationChannelId);
        }
        return notificationBuilder.build();
    }
}