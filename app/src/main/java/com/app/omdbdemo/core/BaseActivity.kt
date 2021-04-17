package com.app.omdbdemo.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.omdbdemo.R
import com.app.omdbdemo.utils.checkInternetConnected
import com.app.omdbdemo.utils.showSnackBar

open class BaseActivity:AppCompatActivity() {
    private var progressDialogFragment: ProgressDialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialogFragment = ProgressDialogFragment.newInstance()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * Show or hide progress dialog
     * @param isShow Boolean
     */
    fun showHideProgressDialog(isShow: Boolean) {
        try {
            if (isShow) {
                if (progressDialogFragment?.dialog == null || progressDialogFragment?.dialog?.isShowing == false || progressDialogFragment?.isAdded == false) {
                    progressDialogFragment?.show(
                        supportFragmentManager,
                        javaClass.simpleName)
                }
            } else {
                progressDialogFragment?.dismissAllowingStateLoss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun isNetworkConnected(): Boolean {
        return if (checkInternetConnected(this@BaseActivity)) {
            true
        } else {
            getString(R.string.msg_check_internet_connection).showSnackBar(this@BaseActivity)
            false
        }
    }
}