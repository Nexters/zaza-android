package com.teamnexters.zaza.ui.alarm

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.teamnexters.zaza.R
import com.teamnexters.zaza.base.BaseActivity
import com.teamnexters.zaza.databinding.ActivityAlarmBinding
import com.teamnexters.zaza.ui.alarm.data.vo.AlarmVO
import com.teamnexters.zaza.util.alarm.AlarmUtil
import com.teamnexters.zaza.util.getAlarm
import com.teamnexters.zaza.util.insertAlarm

class AlarmActivity() : BaseActivity<ActivityAlarmBinding>() {
    override val layoutResourceId: Int = R.layout.activity_alarm
    val TAG = this.javaClass.simpleName

    val alarmVM: AlarmViewModel by lazy {
        ViewModelProviders.of(this).get(AlarmViewModel::class.java)
    }

    val alarmUtil = AlarmUtil.instance

    fun initStartView() {


    }

    /**
     * 두번째로 호출.
     * 데이터 바인딩 및 rxjava 설정.
     * ex) rxjava observe, databinding observe..
     */
    fun initDataBinding() {
        alarmVM.mutableAlarmData.observe(this, Observer{ alarm ->

            for(i in alarm.weeks.indices){
                Log.e(TAG,alarm.weeks[i].toString())
                when(i){
                    0 -> viewDataBinding.checkWeekMon.isChecked = alarm.weeks[i]
                    1 -> viewDataBinding.checkWeekTue.isChecked = alarm.weeks[i]
                    2 -> viewDataBinding.checkWeekWed.isChecked = alarm.weeks[i]
                    3 -> viewDataBinding.checkWeekThu.isChecked = alarm.weeks[i]
                    4 -> viewDataBinding.checkWeekFri.isChecked = alarm.weeks[i]
                    5 -> viewDataBinding.checkWeekSat.isChecked = alarm.weeks[i]
                    6 -> viewDataBinding.checkWeekSun.isChecked = alarm.weeks[i]
                }
            }
            viewDataBinding.tvSleepHour.text = alarm.sleepH.toString()
            viewDataBinding.tvSleepMinute.text = alarm.sleepM.toString()
            viewDataBinding.tvWakeHour.text = alarm.wakeUpH.toString()
            viewDataBinding.tvWakeMinute.text = alarm.wakeUpM.toString()
            viewDataBinding.checkFive.isChecked = alarm.isAfterFive
            viewDataBinding.chckVibrate.isChecked = alarm.isVibrate

         }
        )

    }

    fun setOnOff(isEditable : Boolean){
        if (isEditable){
            viewDataBinding.viewBtns.visibility = View.VISIBLE
            viewDataBinding.btnAlarmEdit.visibility= View.GONE
            viewDataBinding.checkWeekFri.isClickable = true
            viewDataBinding.checkWeekMon.isClickable = true
            viewDataBinding.checkWeekSat.isClickable = true
            viewDataBinding.checkWeekTue.isClickable = true
            viewDataBinding.checkWeekSun.isClickable = true
            viewDataBinding.checkWeekThu.isClickable = true
            viewDataBinding.checkWeekWed.isClickable = true

            viewDataBinding.viewTimeSleep.isClickable = true
            viewDataBinding.viewTimeWake.isClickable = true
        } else{
            viewDataBinding.viewBtns.visibility = View.GONE
            viewDataBinding.viewAlarmBg.setBackgroundColor(Color.WHITE)
            viewDataBinding.btnAlarmEdit.visibility= View.VISIBLE
            viewDataBinding.checkWeekFri.isClickable = false
            viewDataBinding.checkWeekMon.isClickable = false
            viewDataBinding.checkWeekSat.isClickable = false
            viewDataBinding.checkWeekTue.isClickable = false
            viewDataBinding.checkWeekSun.isClickable = false
            viewDataBinding.checkWeekThu.isClickable = false
            viewDataBinding.checkWeekWed.isClickable = false

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
        viewDataBinding.btnAlarmEdit.setOnClickListener {
            setOnOff(true)
        }

        viewDataBinding.viewTimeSleep.setOnClickListener{
            val bottomSheetDialog = AlarmBottomSheetDialog
            bottomSheetDialog.show(supportFragmentManager, "bottomSheet")
        }

        viewDataBinding.btnSaveAlarm.setOnClickListener{
            setOnOff(false)
            val weeks = ArrayList<Boolean>()

            weeks.add(viewDataBinding.checkWeekMon.isChecked)
            weeks.add(viewDataBinding.checkWeekTue.isChecked)
            weeks.add(viewDataBinding.checkWeekWed.isChecked)
            weeks.add(viewDataBinding.checkWeekThu.isChecked)
            weeks.add(viewDataBinding.checkWeekFri.isChecked)
            weeks.add(viewDataBinding.checkWeekSat.isChecked)
            weeks.add(viewDataBinding.checkWeekSun.isChecked)

            val alarmVO = AlarmVO(weeks,viewDataBinding.chckVibrate.isChecked, viewDataBinding.checkFive.isChecked,
                viewDataBinding.tvWakeHour.text.toString().toInt(), viewDataBinding.tvWakeMinute.text.toString().toInt(),
                viewDataBinding.tvSleepHour.text.toString().toInt(), viewDataBinding.tvSleepMinute.text.toString().toInt())
            alarmVM.updateAlarm(alarmVO)
            alarmUtil.registAlarm(this, alarmVO)
        }


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(getAlarm()?.size == 0) {
            Log.e(TAG, "alarm null")
            insertAlarm()
        }

        Log.e(TAG, getAlarm().toString())

        initStartView()
        initDataBinding()
        initAfterBinding()
    }
}
