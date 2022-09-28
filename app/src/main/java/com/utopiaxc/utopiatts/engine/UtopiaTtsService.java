package com.utopiaxc.utopiatts.engine;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
        super.onCreate();
        if (PreferenceManager.getDefaultSharedPreferences(this)
                .getString(SettingsEnum.TTS_DRIVER.getKey(), Driver.AZURE_SDK.getId())
                .equals(Driver.AZURE_SDK.getId())){
            mTts=MsTts.getInstance(getApplicationContext());
        }else{
            mTts=WsTts.getInstance(getApplicationContext());
        }
        mTts = MsTts.getInstance(getApplicationContext());
        startForegroundService();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        mTts.stopSpeak();
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
    }

    //disable audio format wrong
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
        synthesisCallback.start(OutputFormat.RAW_48K_HZ_16_BIT_MONO_PCM.getSoundFrequency(),
                OutputFormat.RAW_48K_HZ_16_BIT_MONO_PCM.getAudioFormat(), 1);
        mTts.doSpeak(synthesisRequest.getCharSequenceText().toString(),
                synthesisRequest.getPitch(), synthesisRequest.getSpeechRate());
    }

    private void startForegroundService() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(notificationChannelId, notificationName, NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(notificationChannel);

        }
        startForeground(NOTIFICATION_ID, getNotification());
    }

    private Notification getNotification() {
        Intent stopSelf = new Intent(this, UtopiaTtsService.class);
        stopSelf.setAction(ACTION_STOP_SERVICE);
        PendingIntent pStopSelf = PendingIntent.getService(this, 0, stopSelf, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
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