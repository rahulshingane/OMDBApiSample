package com.app.omdbdemo.core

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.app.omdbdemo.R
import com.app.omdbdemo.databinding.InflateProgressViewBinding

/**
 * This is a progress dialog fragment to show progress on api call
 * @property binding [ERROR : null type]
 */
class ProgressDialogFragment : DialogFragment() {

    var binding: InflateProgressViewBinding? = null

    companion object {

        var FRAGMENT_TAG = "dialog"
        fun newInstance(): ProgressDialogFragment {
            val dialogFragment = ProgressDialogFragment()
            dialogFragment.isCancelable = false
            return dialogFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return InflateProgressViewBinding.inflate(inflater, container, false).let {
            it.progressView.visibility = View.VISIBLE
            binding = it
            it.root
        }

    }

    override fun show(manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
    }

    override fun dismiss() {
        binding?.progressView?.visibility = View.GONE
        if (dialog != null)
            super.dismiss()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onStart() {
        super.onStart()

        val window = dialog?.window
        val windowParams = window?.attributes
        windowParams?.dimAmount = 0f

        window?.decorView?.setBackgroundResource(R.color.transparent)
        windowParams?.flags = windowParams!!.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
        window.attributes = windowParams
    }
}