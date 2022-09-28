package com.utopiaxc.utopiatts.fragments;

import android.os.Bundle;

import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;


import com.utopiaxc.utopiatts.R;

import java.util.Objects;

public class FragmentTtsSettings extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        EditTextPreference azureToken = findPreference("azure_token");
        assert azureToken != null;
        azureToken.setShouldDisableView(true);
    }
}