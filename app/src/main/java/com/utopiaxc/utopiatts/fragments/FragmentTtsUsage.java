package com.utopiaxc.utopiatts.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.utopiaxc.utopiatts.databinding.FragmentTtsUsageBinding;

import com.utopiaxc.utopiatts.R;

public class FragmentTtsUsage extends Fragment {
    public FragmentTtsUsage() {

    }

    public static FragmentTtsUsage newInstance() {
        return new FragmentTtsUsage();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tts_usage, container, false);
    }
}