package com.app.omdbdemo.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.andrognito.flashbar.Flashbar
import com.app.omdbdemo.R
import com.app.omdbdemo.utils.Constants.Companion.SNAKBAR_TYPE_ERROR
import com.app.omdbdemo.utils.Constants.Companion.SNAKBAR_TYPE_MESSAGE
import com.app.omdbdemo.utils.Constants.Companion.SNAKBAR_TYPE_SUCCESS
import hari.bounceview.BounceView

/*
* Kotlin Extension functions for view
* */

/**
 * to show keyboard
 */
fun View.showKeyBoard() {
    this.postDelayed({
        val inputManager = this.context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(this, InputMethodManager.HIDE_NOT_ALWAYS)
    }, 600)
}

/**
 * to hide keyboard
 */
fun View.hideKeyBoard() {
    val inputManager = this.context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * Hide keyboard on touch
 */
@SuppressLint("ClickableViewAccessibility")
fun View.hideKeyboardOnTouch() {
    setOnTouchListener { _, _ ->
        hideKeyBoard()
        false
    }
}

fun View.makeBounceable() {
    BounceView.addAnimTo(this).setScaleForPushInAnim(0.94f, 0.94f).setScaleForPopOutAnim(1.03f, 1.03f)
}

/**
 * Focus on given view and open soft keyboard
 * @receiver View  Root view
 * @param viewId Int? Id of view on which we need do focus
 * @param showKeyBoard Boolean show keyboard or not
 */
fun View.focusOnField(viewId: Int?, showKeyBoard: Boolean = true) {
    viewId?.apply {
        this@focusOnField.findViewById<View>(viewId)?.requestFocus()
    }
    if (showKeyBoard) this.showKeyBoard()
}


fun View.clickWithDebounce(debounceTime: Long = 1000L, shouldHideKeyBoard:Boolean = true,action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0
        override fun onClick(v: View) {
            if(shouldHideKeyBoard)
                hideKeyBoard()
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action()
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

fun hideKeyboard(activity: Activity?) {
    val view = activity?.findViewById<View>(android.R.id.content)
    if (view != null) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

var flashbar: Flashbar? = null

fun String.showSnackBar(context: Activity?) {
    showSnackBar(context, SNAKBAR_TYPE_ERROR, null)
}

fun String.showSnackBar(
    context: Activity?,
    type: Int,
    dismissListener: Flashbar.OnBarDismissListener? = null,
    duration: Long = 3000
) {
    if (context != null) {
        var color = R.color.colorAccent
        when (type) {
            SNAKBAR_TYPE_ERROR -> {
                color = R.color.colorRed
            }
            SNAKBAR_TYPE_SUCCESS -> {
                color = R.color.colorGreen
            }
            SNAKBAR_TYPE_MESSAGE -> {
                color = R.color.colorBlue
            }
        }

        if (((flashbar?.isShowing() == true) || (flashbar?.isShown() == true))) {
            flashbar?.dismiss()
        }

        val builder = Flashbar.Builder(context)

        builder
            .gravity(Flashbar.Gravity.TOP)
            .title(context.getString(R.string.app_name))
            .message(this)
            .backgroundColorRes(color)
            .enableSwipeToDismiss()
            .duration(duration)
        if (dismissListener != null)
            builder.barDismissListener(dismissListener)

        flashbar = builder.build()
        flashbar?.show()
    }
}



