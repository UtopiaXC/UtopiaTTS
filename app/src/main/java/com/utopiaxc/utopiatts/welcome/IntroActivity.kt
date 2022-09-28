package com.utopiaxc.utopiatts.welcome

import android.os.Bundle
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroFragment
import com.utopiaxc.utopiatts.R
import com.utopiaxc.utopiatts.utils.ThemeUtil

class IntroActivity : AppIntro2() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Color gradient
        isWizardMode = true
        isColorTransitionsEnabled = true
        supportActionBar?.hide()
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


    }
}