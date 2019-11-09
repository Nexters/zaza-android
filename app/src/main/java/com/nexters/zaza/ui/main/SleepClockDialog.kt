package com.nexters.zaza.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.nexters.zaza.R
import kotlinx.android.synthetic.main.dialog_sleep_clock.view.*

class SleepClockDialog : DialogFragment() {

    lateinit var subtext: String
    lateinit var onClick: View.OnClickListener
    companion object {
        fun getInstance(subtext: String): SleepClockDialog {
            val dialog = SleepClockDialog()
            dialog.subtext = subtext
            return dialog
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_sleep_clock, container)

        dialog!!.window.setBackgroundDrawableResource(R.drawable.bg_r21)
        /*view.text_sleep_clock_button.setOnClickListener{
            val stopIntent = Intent()
            stopIntent.putExtra("wake","wake")
            stopIntent.putExtra("state","alarmOff")
            context!!.sendBroadcast(stopIntent)
            vibrator.cancel()
            dismissAllowingStateLoss()
        }*/
        view.text_sleep_clock_button.setOnClickListener(onClick)

        view.text_sleep_clock_subtext.text = subtext

        return view
    }

    fun setOnClickListener(onClickListener: View.OnClickListener){
        this.onClick = onClickListener
    }


}