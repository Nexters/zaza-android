package com.teamnexters.zaza.ui.alarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.teamnexters.zaza.R
import com.teamnexters.zaza.databinding.ActivityAlarmBinding

class AlarmActivity : AppCompatActivity() {

    private var layoutRes = getRayoutRes()
    private lateinit var binding : ActivityAlarmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        val bottomSheetDialog = AlarmBottomSheetDialog().getInstance()

        bottomSheetDialog.show(supportFragmentManager,"bottomSheet");

    }

    fun subscribeUi(){

    }

    fun getRayoutRes() : Int{
        return R.layout.activity_alarm
    }
}
