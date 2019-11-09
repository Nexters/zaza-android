package com.nexters.zaza.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.nexters.zaza.R
import kotlinx.android.synthetic.main.dialog_cancel_sleep.view.*

class CancelSleepDialog : DialogFragment() {

    lateinit var callback: (Boolean) -> Unit
    var totalSec = 0

    companion object {
        fun getInstance(totalSec: Int, callback: (Boolean) -> Unit): CancelSleepDialog {
            val dialog = CancelSleepDialog()
            dialog.callback = callback
            dialog.totalSec = totalSec
            return dialog
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_cancel_sleep, container)

        if (totalSec < (2 * 60 * 60)) {
            view.text_cancel_message.text = "2시간 미만의 수면은 기록되지 않습니다. 그래도 켜시겠습니까?"
        }

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