package com.utopiaxc.utopiatts.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.utopiaxc.utopiatts.R;

public class FragmentTtsSettings extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}