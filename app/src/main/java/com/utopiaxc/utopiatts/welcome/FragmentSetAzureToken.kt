package com.utopiaxc.utopiatts.welcome

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.github.appintro.SlidePolicy
import com.utopiaxc.utopiatts.R
import com.utopiaxc.utopiatts.databinding.FragmentSetAzureTokenBinding
import com.utopiaxc.utopiatts.enums.SettingsEnum
import com.utopiaxc.utopiatts.tts.MsTts
import com.utopiaxc.utopiatts.tts.enums.Regions
import com.utopiaxc.utopiatts.utils.ThemeUtil


class FragmentSetAzureToken(private var mContext: IntroActivity) : Fragment(), SlidePolicy {
    private val TAG = "FragmentSetAzureToken"
    private lateinit var mBinding: FragmentSetAzureTokenBinding
    private var mIsChecked=false
    private lateinit var mFragmentSetAzureTokenHandler : FragmentSetAzureTokenHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = FragmentSetAzureTokenBinding.inflate(layoutInflater)
        mFragmentSetAzureTokenHandler=FragmentSetAzureTokenHandler(mContext.mainLooper)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (ThemeUtil.isNightMode(mContext)){
            mBinding.root.setBackgroundColor( mContext.getColor(R.color.welcome_bg_night))
        }else{
            mBinding.root.setBackgroundColor( mContext.getColor(R.color.welcome_bg_day))
        }

        mBinding.buttonSetAzureToken.setOnClickListener{
            val imm: InputMethodManager = requireView().context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireView().windowToken, 0)
            val token=mBinding.editTextAzureToken.text.toString()
            if (token== ""){
                AlertDialog.Builder(this.activity).setTitle(R.string.warning)
                    .setMessage(R.string.warning_blank_token)
                    .setPositiveButton(R.string.confirm, null)
                    .create()
                    .show()
                return@setOnClickListener
            }
            val region = Regions.getRegion(resources.getStringArray(R.array.azure_region_values)
                    [mBinding.spinnerAzureRegion.selectedItemId.toInt()]).id
            mBinding.buttonSetAzureToken.isEnabled=false
            mBinding.buttonSetAzureToken.text=getString(R.string.checking)
            Thread(CheckToken(token,region)).start()
        }
        mBinding.buttonAzureTokenHelp.setOnClickListener {
            AlertDialog.Builder(this.activity).setTitle(R.string.tips)
                .setMessage(R.string.tips_azure_token)
                .setPositiveButton(R.string.confirm, null)
                .setNegativeButton(R.string.tutorials) { _, _ ->
                    run {
                        AlertDialog.Builder(this.activity).setTitle(R.string.sorry)
                            .setMessage(R.string.tutorials_is_not_ready)
                            .setPositiveButton(R.string.confirm, null)
                            .create()
                            .show()
                    }
                }
                .create()
                .show()
        }
        return mBinding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(context: IntroActivity): FragmentSetAzureToken {
            return FragmentSetAzureToken(context)
        }
    }

    override val isPolicyRespected: Boolean
        get() = mIsChecked

    override fun onUserIllegallyRequestedNextPage() {
        AlertDialog.Builder(this.activity).setTitle(R.string.warning)
            .setMessage(R.string.warning_azure_token_not_checked)
            .setPositiveButton(R.string.confirm, null)
            .create()
            .show()
    }

    private inner class CheckToken(var token: String, var region: String) : Runnable{

        override fun run() {
            if (MsTts.testAzureConfig(token,region)){
                val message=Message()
                message.what=mFragmentSetAzureTokenHandler.CHECK_TOKEN_SUCCESS
                val bundle=Bundle()
                bundle.putString(SettingsEnum.AZURE_TOKEN.key,token)
                bundle.putString(SettingsEnum.AZURE_REGION.key,Regions.getRegion(region).getName())
                message.data=bundle
                mFragmentSetAzureTokenHandler.sendMessage(message)
            }else{
                mFragmentSetAzureTokenHandler.sendEmptyMessage(
                    mFragmentSetAzureTokenHandler.CHECK_TOKEN_ERROR)
            }
        }
    }

    private inner class FragmentSetAzureTokenHandler(looper: Looper) : Handler(looper){
        val CHECK_TOKEN_SUCCESS=0x01
        val CHECK_TOKEN_ERROR=0x02

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what){
                CHECK_TOKEN_SUCCESS->{
                    val bundle=msg.data
                    mBinding.buttonSetAzureToken.isEnabled=true
                    mBinding.buttonSetAzureToken.text=getString(R.string.success)
                    mBinding.buttonSetAzureToken
                        .setBackgroundColor(resources.getColor(R.color.success))
                    val editor= PreferenceManager.getDefaultSharedPreferences(mContext).edit()
                    editor.putString(SettingsEnum.AZURE_TOKEN.key,bundle
                        .getString(SettingsEnum.AZURE_TOKEN.key))
                    editor.putString(SettingsEnum.AZURE_REGION.key,bundle
                        .getString(SettingsEnum.AZURE_REGION.key))
                    editor.apply()
                    mIsChecked=true
                }
                CHECK_TOKEN_ERROR->{
                    mBinding.buttonSetAzureToken.isEnabled=true
                    mBinding.buttonSetAzureToken
                        .setBackgroundColor(resources.getColor(R.color.warning))
                    mBinding.buttonSetAzureToken.text=getString(R.string.error)
                    AlertDialog.Builder(mContext).setTitle(R.string.error)
                        .setMessage(R.string.error_azure_token)
                        .setPositiveButton(R.string.confirm, null)
                        .create()
                        .show()
                }
            }
        }
    }
}