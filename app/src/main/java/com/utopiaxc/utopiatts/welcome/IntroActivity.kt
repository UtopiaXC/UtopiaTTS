package com.utopiaxc.utopiatts.welcome

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroFragment
import com.utopiaxc.utopiatts.MainActivity
import com.utopiaxc.utopiatts.R
import com.utopiaxc.utopiatts.enums.SettingsEnum
import com.utopiaxc.utopiatts.utils.ThemeUtil

class IntroActivity : AppIntro2() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isWizardMode = true
        supportActionBar?.hide()
        setSwipeLock(true)

        val stringColor: Int
        val bgColor: Int
        if (ThemeUtil.isNightMode(this)) {
            stringColor = R.color.welcome_string_night
            bgColor = R.color.welcome_bg_night
        } else {
            stringColor = R.color.welcome_string_day
            bgColor = R.color.welcome_bg_day
        }

        //First Fragment
        addSlide(
                AppIntroFragment.createInstance(
                        getString(R.string.welcome),
                        getString(R.string.rights),
                        R.mipmap.ic_launcher,
                        bgColor,
                        stringColor,
                        stringColor,
                )
        )

        addSlide(FragmentTtsSelect.newInstance(this))

        addSlide(FragmentSetAzureToken.newInstance(this))

    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        val editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        editor.putBoolean(SettingsEnum.FIRST_BOOT.key, false)
        editor.apply()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}