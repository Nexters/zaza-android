package com.teamnexters.zaza.ui.alarm

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil.setContentView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.teamnexters.zaza.R
import com.teamnexters.zaza.base.BaseActivity
import com.teamnexters.zaza.databinding.ActivityAlarmBinding

class AlarmActivity() : BaseActivity<ActivityAlarmBinding>() {
    override val layoutResourceId: Int = R.layout.activity_alarm


    fun initStartView() {


    }

    /**
     * 두번째로 호출.
     * 데이터 바인딩 및 rxjava 설정.
     * ex) rxjava observe, databinding observe..
     */
    fun initDataBinding() {

    }

    fun setOnOff(isEditable : Boolean){
        if (isEditable){
            viewDataBinding.viewAlarmBg.setBackgroundColor(Color.BLACK)
            viewDataBinding.btnAlarmEdit.visibility= View.GONE
            viewDataBinding.btnWeekFri.isClickable = true
            viewDataBinding.btnWeekMon.isClickable = true
            viewDataBinding.btnWeekSat.isClickable = true
            viewDataBinding.btnWeekTue.isClickable = true
            viewDataBinding.btnWeekSun.isClickable = true
            viewDataBinding.btnWeekThu.isClickable = true
            viewDataBinding.btnWeekWed.isClickable = true

            viewDataBinding.viewTimeSleep.isClickable = true
            viewDataBinding.viewTimeWake.isClickable = true
        } else{
            viewDataBinding.viewAlarmBg.setBackgroundColor(Color.WHITE)
            viewDataBinding.btnAlarmEdit.visibility= View.VISIBLE
            viewDataBinding.btnWeekFri.isClickable = false
            viewDataBinding.btnWeekMon.isClickable = false
            viewDataBinding.btnWeekSat.isClickable = false
            viewDataBinding.btnWeekTue.isClickable = false
            viewDataBinding.btnWeekSun.isClickable = false
            viewDataBinding.btnWeekThu.isClickable = false
            viewDataBinding.btnWeekWed.isClickable = false

            viewDataBinding.viewTimeSleep.isClickable = false
            viewDataBinding.viewTimeWake.isClickable = false
        }
    }


    /**
     * 바인딩 이후에 할 일을 여기에 구현.
     * 그 외에 설정할 것이 있으면 이곳에서 설정.
     * 클릭 리스너도 이곳에서 설정.
     */

    fun initAfterBinding() {
        viewDataBinding.btnAlarmEdit.setOnClickListener { v ->
            setOnOff(true)
        }

        viewDataBinding.viewTimeSleep.setOnClickListener{v ->
            val bottomSheetDialog = AlarmBottomSheetDialog
            bottomSheetDialog.show(supportFragmentManager, "bottomSheet")
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStartView()
        initDataBinding()
        initAfterBinding()
    }
}
