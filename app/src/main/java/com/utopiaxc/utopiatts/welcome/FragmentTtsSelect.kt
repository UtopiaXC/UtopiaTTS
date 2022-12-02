package com.utopiaxc.utopiatts.welcome

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.github.appintro.SlidePolicy
import com.utopiaxc.utopiatts.MainActivity
import com.utopiaxc.utopiatts.R
import com.utopiaxc.utopiatts.databinding.FragmentTtsSelectBinding
import com.utopiaxc.utopiatts.enums.SettingsEnum
import com.utopiaxc.utopiatts.tts.enums.Driver
import com.utopiaxc.utopiatts.utils.ThemeUtil


class FragmentTtsSelect(private var mContext: IntroActivity) : Fragment(), SlidePolicy {
    private val TAG = "FragmentTtsSelect"
    private var mIsChecked = true
    private lateinit var mBinding: FragmentTtsSelectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = FragmentTtsSelectBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext)
        val editor = sharedPreferences.edit()
        editor.putString(
            SettingsEnum.TTS_DRIVER.key,
            SettingsEnum.TTS_DRIVER.defaultValue as String
        )
        editor.apply()
        if (ThemeUtil.isNightMode(mContext)) {
            mBinding.root.setBackgroundColor(mContext.getColor(R.color.welcome_bg_night))
            mBinding.buttonTtsFromSelectHelp.setImageResource(R.drawable.ic_baseline_help_outline_24_night)
        } else {
            mBinding.root.setBackgroundColor(mContext.getColor(R.color.welcome_bg_day))
        }
        mBinding.buttonTtsFromSelectHelp.setOnClickListener {
            AlertDialog.Builder(this.activity).setTitle(R.string.tips)
                .setMessage(R.string.tips_tts_driver)
                .setPositiveButton(R.string.confirm, null)
                .setNeutralButton(R.string.ag2s20150909_tts_title) { _, _ ->
                    run {
                        val uri: Uri = Uri.parse(getString(R.string.ag2s20150909_tts))
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                    }
                }
                .create()
                .show()
        }
        mBinding.ttsFromSdk.setOnClickListener {
            Log.d(TAG, "ttsFromSdk clicked")
            editor.putString(SettingsEnum.TTS_DRIVER.key, Driver.AZURE_SDK.id)
            editor.apply()
            mIsChecked=true
        }
        mBinding.ttsFromWebsocket.setOnClickListener {
            Log.d(TAG, "ttsFromWebsocket clicked")
            editor.putString(SettingsEnum.TTS_DRIVER.key, Driver.WEBSOCKET.id)
            editor.apply()
            mIsChecked=false
        }
        return mBinding.root
    }

    override val isPolicyRespected: Boolean
        get() = mIsChecked

    override fun onUserIllegallyRequestedNextPage() {
        val editor = PreferenceManager.getDefaultSharedPreferences(mContext).edit()
        editor.putBoolean(SettingsEnum.FIRST_BOOT.key, false)
        editor.apply()
        mContext.startActivity(Intent(mContext, MainActivity::class.java))
        mContext.finish()
    }

    companion object {
        @JvmStatic
        fun newInstance(context: IntroActivity): FragmentTtsSelect {
            return FragmentTtsSelect(context)
        }
    }
}