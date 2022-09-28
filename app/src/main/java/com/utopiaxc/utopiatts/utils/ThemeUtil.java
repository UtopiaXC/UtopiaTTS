package com.utopiaxc.utopiatts.utils;

import android.content.Context;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

import com.utopiaxc.utopiatts.enums.ThemeModeEnum;

public class ThemeUtil {
    public static void setThemeMode(String mode) {
        if (mode.equals(ThemeModeEnum.AUTO_MODE.getMode())) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        } else if (mode.equals(ThemeModeEnum.NIGHT_MODE.getMode())) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if (mode.equals(ThemeModeEnum.DAY_MODE.getMode())) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public static boolean isNightMode(Context context){
        int mode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return mode == Configuration.UI_MODE_NIGHT_YES;
    }
}
