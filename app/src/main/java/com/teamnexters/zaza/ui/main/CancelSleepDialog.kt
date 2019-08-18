package com.teamnexters.zaza.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.teamnexters.zaza.R
import kotlinx.android.synthetic.main.dialog_cancel_sleep.view.*

class CancelSleepDialog : DialogFragment() {

    lateinit var callback: (Boolean) -> Unit

    companion object {
        fun getInstance(callback: (Boolean) -> Unit): CancelSleepDialog {
            val dialog = CancelSleepDialog()
            dialog.callback = callback
            return dialog
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_cancel_sleep, container)

        dialog!!.window.setBackgroundDrawableResource(R.drawable.bg_gradient_black_r21)
        view.text_cancel_sleep_yes.setOnClickListener {
            callback.invoke(true)
            dismissAllowingStateLoss()
        }
        view.text_cancel_sleep_no.setOnClickListener {
            callback.invoke(false)
        }

        return view
    }
}