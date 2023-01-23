package com.utopiaxc.utopiatts.fragments;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;

import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;


import com.utopiaxc.utopiatts.R;
import com.utopiaxc.utopiatts.enums.SettingsEnum;
import com.utopiaxc.utopiatts.tts.MsTts;
import com.utopiaxc.utopiatts.tts.Tts;
import com.utopiaxc.utopiatts.tts.WsTts;
import com.utopiaxc.utopiatts.tts.enums.Actors;
import com.utopiaxc.utopiatts.tts.enums.Driver;
import com.utopiaxc.utopiatts.tts.enums.Regions;

import java.util.Objects;

public class FragmentTtsSettings extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        Tts tts;
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(requireActivity());
        if (sharedPreferences.getString(SettingsEnum.TTS_DRIVER.getKey(), Driver.AZURE_SDK.getId())
                .equals(Driver.AZURE_SDK.getId())) {
            tts = MsTts.getInstance(requireActivity().getApplicationContext());
        } else {
            tts = WsTts.getInstance(requireActivity().getApplicationContext());
        }
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        EditTextPreference azureToken = findPreference(SettingsEnum.AZURE_TOKEN.getKey());
        Objects.requireNonNull(azureToken).setOnBindEditTextListener(editText -> editText
                .setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD));
        azureToken.setSummary(null);
        azureToken.setOnPreferenceChangeListener((preference, newValue) -> {
            boolean ok = MsTts.testAzureConfig((String) newValue,
                    Regions.getRegion(PreferenceManager
                                    .getDefaultSharedPreferences(requireActivity())
                                    .getString(SettingsEnum.AZURE_REGION.getKey(),
                                            (String) SettingsEnum.AZURE_REGION.getDefaultValue()))
                            .getId());
            if (ok) {
                new AlertDialog.Builder(requireActivity()).setTitle(R.string.success)
                        .setMessage(R.string.success_azure_token)
                        .setPositiveButton(R.string.confirm, null)
                        .create()
                        .show();
            } else {
                new AlertDialog.Builder(requireActivity()).setTitle(R.string.error)
                        .setMessage(R.string.error_azure_token)
                        .setPositiveButton(R.string.confirm, null)
                        .create()
                        .show();
            }
            tts.initTts();
            return ok;
        });

        ListPreference listActors = findPreference(SettingsEnum.ACTOR.getKey());
        assert listActors != null;
        listActors.setOnPreferenceChangeListener((preference, newValue) -> {
            if (Actors.getActor((String) newValue).isPre()) {
                if (Regions.getRegion(sharedPreferences.getString(SettingsEnum.AZURE_REGION
                                        .getKey(),
                                String.valueOf(SettingsEnum.AZURE_REGION.getDefaultValue())))
                        .isNotSupportPre()) {
                    new AlertDialog.Builder(requireActivity()).setTitle(R.string.error)
                            .setMessage(R.string.region_not_support_pre)
                            .setPositiveButton(R.string.confirm, null)
                            .create()
                            .show();
                    return false;
                }
            }
            tts.initTts();
            return true;
        });

        ListPreference listRegion = findPreference(SettingsEnum.AZURE_REGION.getKey());
        assert listRegion != null;
        listRegion.setOnPreferenceChangeListener((preference, newValue) -> {
            if (Actors.getActor(sharedPreferences.getString(SettingsEnum.ACTOR.getKey(),
                    (String) SettingsEnum.ACTOR.getDefaultValue())).isPre()) {
                if (Regions.getRegion((String) newValue).isNotSupportPre()) {
                    new AlertDialog.Builder(requireActivity()).setTitle(R.string.error)
                            .setMessage(R.string.actor_is_pre)
                            .setPositiveButton(R.string.confirm, null)
                            .create()
                            .show();
                }
                return false;
            }
            return true;
        });

        ListPreference listFormat = findPreference(SettingsEnum.OUTPUT_FORMAT.getKey());
        assert listFormat != null;
        listFormat.setOnPreferenceChangeListener((preference, newValue) -> {
            new AlertDialog.Builder(requireActivity()).setTitle(R.string.warning)
                    .setMessage(R.string.warning_of_output_format)
                    .setPositiveButton(R.string.confirm, null)
                    .create()
                    .show();
            return true;
        });

        EditTextPreference editTextPreferenceToken = findPreference(
                SettingsEnum.AZURE_TOKEN.getKey());
        assert editTextPreferenceToken != null;
        editTextPreferenceToken.setEnabled(Driver.AZURE_SDK.getId().equals(
                sharedPreferences.getString(SettingsEnum.TTS_DRIVER.getKey(),
                        String.valueOf(SettingsEnum.TTS_DRIVER.getDefaultValue()))));

        ListPreference listDriver = findPreference(SettingsEnum.TTS_DRIVER.getKey());
        assert listDriver != null;
        listDriver.setOnPreferenceChangeListener((preference, newValue) -> {
            editTextPreferenceToken.setEnabled(Driver.AZURE_SDK.getId().equals(newValue));
            return true;
        });
    }
}