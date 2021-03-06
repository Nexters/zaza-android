package com.nexters.zaza.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.nexters.zaza.R
import kotlinx.android.synthetic.main.dialog_sleep_ready.view.*

class SleepReadyDialog : DialogFragment() {

    companion object {
        fun getInstance(): SleepReadyDialog {
            val dialog = SleepReadyDialog()
            return dialog
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_sleep_ready, container)

        dialog!!.window.setBackgroundDrawableResource(R.drawable.bg_r21)

        view.text_sleep_ready.setOnClickListener {
            dismissAllowingStateLoss()
        }

        return view
    }
}