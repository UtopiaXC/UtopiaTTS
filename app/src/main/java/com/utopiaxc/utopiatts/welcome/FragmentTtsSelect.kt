package com.utopiaxc.utopiatts.welcome

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.appintro.SlideBackgroundColorHolder
import com.github.appintro.SlidePolicy
import com.utopiaxc.utopiatts.R
import com.utopiaxc.utopiatts.databinding.FragmentTtsSelectBinding
import com.utopiaxc.utopiatts.utils.ThemeUtil


class FragmentTtsSelect(private var mContext: IntroActivity) : Fragment(), SlidePolicy,
    SlideBackgroundColorHolder {
    private lateinit var mBinding: FragmentTtsSelectBinding
    private var mColorDayRes = R.color.welcome_bg_day
    private var mColorDay = mContext.getColor(mColorDayRes)
    private var mColorNightRes = R.color.welcome_bg_night
    private var mColorNight = mContext.getColor(mColorNightRes)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = FragmentTtsSelectBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tts_select, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(context: IntroActivity): FragmentTtsSelect {
            return FragmentTtsSelect(context)
        }
    }

    @Deprecated(
        "`defaultBackgroundColor` has been deprecated to support configuration changes",
        replaceWith = ReplaceWith("defaultBackgroundColorRes")
    )
    override val defaultBackgroundColor: Int
        get() {
            return if (ThemeUtil.isNightMode(mContext)) {
                mColorNight
            } else {
                mColorDay
            }
        }

    override val defaultBackgroundColorRes: Int
        get() {
            return if (ThemeUtil.isNightMode(mContext)) {
                mColorNightRes
            } else {
                mColorDayRes
            }
        }

    override fun setBackgroundColor(backgroundColor: Int) {
        mBinding.root.setBackgroundColor(backgroundColor)
    }

    override val isPolicyRespected: Boolean
        get() = true

    override fun onUserIllegallyRequestedNextPage() {
        AlertDialog.Builder(this.activity).setTitle("")
            .setMessage("")
            .setPositiveButton("", null)
            .create()
            .show()
    }

}