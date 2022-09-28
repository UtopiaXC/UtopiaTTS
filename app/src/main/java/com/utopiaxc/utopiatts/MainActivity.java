package com.utopiaxc.utopiatts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.utopiaxc.utopiatts.databinding.ActivityMainBinding;
import com.utopiaxc.utopiatts.enums.SettingsEnum;
import com.utopiaxc.utopiatts.tts.MsTts;
import com.utopiaxc.utopiatts.utils.ThemeUtil;
import com.utopiaxc.utopiatts.welcome.IntroActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.utopiaxc.utopiatts.databinding.ActivityMainBinding binding =
                ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Night mode
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        ThemeUtil.setThemeMode(sharedPreferences.getString(SettingsEnum.THEME.getKey(),
                (String) SettingsEnum.THEME.getDefaultValue()));
        //First install check
        if (sharedPreferences.getBoolean(SettingsEnum.FIRST_BOOT.getKey(),
                (Boolean) SettingsEnum.FIRST_BOOT.getDefaultValue())) {
            //Welcome
            Intent intent = new Intent(this, IntroActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        MsTts.getInstance(getApplicationContext());
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.fragment_tts_settings, R.id.fragment_tts_usage, R.id.fragment_about)
                .build();
        NavController navController =
                Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI
                .setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}