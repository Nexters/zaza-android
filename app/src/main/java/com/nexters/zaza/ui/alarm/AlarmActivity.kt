package com.nexters.zaza.ui.alarm

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nexters.zaza.R
import com.nexters.zaza.base.BaseActivity
import com.nexters.zaza.databinding.ActivityAlarmBinding
import com.nexters.zaza.ui.alarm.data.vo.AlarmVO
import com.nexters.zaza.util.SharedUtil
import com.nexters.zaza.util.alarm.AlarmUtil
import com.nexters.zaza.util.getAlarm
import com.nexters.zaza.util.insertAlarm
import java.util.*
import kotlin.collections.ArrayList

class AlarmActivity() : BaseActivity<ActivityAlarmBinding>(), CompoundButton.OnCheckedChangeListener {


    override val layoutResourceId: Int = R.layout.activity_alarm
    val TAG = this.javaClass.simpleName

    val alarmVM: AlarmViewModel by lazy {
        ViewModelProviders.of(this).get(AlarmViewModel::class.java)
    }

    val alarmUtil = AlarmUtil.instance
    lateinit var sharedUtil: SharedUtil

    fun initStartView() {
        sharedUtil = SharedUtil(this)
    }

    /**
     * 두번째로 호출.
     * 데이터 바인딩 및 rxjava 설정.
     * ex) rxjava observe, databinding observe..
     */
    fun initDataBinding() {
        alarmVM.mutableAlarmData.observe(this, Observer { alarm ->

            Log.e(TAG, "getAlarm")
            for (i in alarm.weeks.indices) {
                Log.e(TAG, alarm.weeks[i].toString())
                when (i) {
                    0 -> viewDataBinding.checkWeekMon.isChecked = alarm.weeks[i]
                    1 -> viewDataBinding.checkWeekTue.isChecked = alarm.weeks[i]
                    2 -> viewDataBinding.checkWeekWed.isChecked = alarm.weeks[i]
                    3 -> viewDataBinding.checkWeekThu.isChecked = alarm.weeks[i]
                    4 -> viewDataBinding.checkWeekFri.isChecked = alarm.weeks[i]
                    5 -> viewDataBinding.checkWeekSat.isChecked = alarm.weeks[i]
                    6 -> viewDataBinding.checkWeekSun.isChecked = alarm.weeks[i]
                }
            }
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, alarm.sleepH)
            calendar.set(Calendar.MINUTE, alarm.sleepM)
            calendar.add(Calendar.MINUTE, 30)
            viewDataBinding.tvSleepHour.text = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY))
            viewDataBinding.tvSleepMinute.text = String.format("%02d", calendar.get(Calendar.MINUTE))
            viewDataBinding.tvWakeHour.text = String.format("%02d", alarm.wakeUpH)
            viewDataBinding.tvWakeMinute.text = String.format("%02d", alarm.wakeUpM)
            viewDataBinding.checkFive.isChecked = alarm.isAfterFive
            viewDataBinding.chckVibrate.isChecked = alarm.isVibrate

            var wh = alarm.wakeUpH
            if ((alarm.sleepH > alarm.wakeUpH) || ((alarm.sleepH == alarm.wakeUpM) && (alarm.sleepM >= alarm.wakeUpM)))
                wh += 24

            wh = Math.abs(alarm.sleepH - wh)

            viewDataBinding.tvSleepTime.text = "+$wh"

            val text = sharedUtil.getStringPreference(SharedUtil.ALARM_TEXT, " ")

            viewDataBinding.etAlarm.setText(text)

        })

    }

    fun setOnOff(isEditable: Boolean) {
        if (isEditable) {
            viewDataBinding.viewBtns.visibility = View.VISIBLE

            viewDataBinding.checkWeekFri.isClickable = true
            viewDataBinding.checkWeekMon.isClickable = true
            viewDataBinding.checkWeekSat.isClickable = true
            viewDataBinding.checkWeekTue.isClickable = true
            viewDataBinding.checkWeekSun.isClickable = true
            viewDataBinding.checkWeekThu.isClickable = true
            viewDataBinding.checkWeekWed.isClickable = true


            viewDataBinding.viewTimeSleep.isClickable = true
            viewDataBinding.viewTimeWake.isClickable = true
        } else {
            viewDataBinding.viewBtns.visibility = View.GONE
            viewDataBinding.viewAlarmBg.setBackgroundColor(Color.WHITE)
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

        val alarmDialog = AlarmBottomSheetDialog()
        alarmDialog.setActivityBinding(viewDataBinding)

        viewDataBinding.viewTimeSleep.setOnClickListener {
            alarmDialog.show(supportFragmentManager, "sleep")
            viewDataBinding.viewTimeSleep.background = getDrawable(R.drawable.bg_round_purple)
            viewDataBinding.ivMoonAlarm.setImageDrawable(getDrawable(R.drawable.moon_white))
            viewDataBinding.tvSleepMinute.setTextColor(getColor(R.color.white))
            viewDataBinding.tvSleepColon.setTextColor(getColor(R.color.white))
            viewDataBinding.tvSleepHour.setTextColor(getColor(R.color.white))

            viewDataBinding.viewTimeWake.background = getDrawable(R.drawable.bg_stroke_purple)
            viewDataBinding.ivSunAlarm.setImageDrawable(getDrawable(R.drawable.sun_blue))
            viewDataBinding.tvWakeMinute.setTextColor(getColor(R.color.purple))
            viewDataBinding.tvWakeHour.setTextColor(getColor(R.color.purple))
            viewDataBinding.tvWakeColon.setTextColor(getColor(R.color.purple))
        }
        viewDataBinding.viewTimeWake.setOnClickListener {
            alarmDialog.show(supportFragmentManager, "wake")

            viewDataBinding.viewTimeSleep.background = getDrawable(R.drawable.bg_stroke_purple)
            viewDataBinding.ivMoonAlarm.setImageDrawable(getDrawable(R.drawable.moon_blue))
            viewDataBinding.tvSleepMinute.setTextColor(getColor(R.color.purple))
            viewDataBinding.tvSleepColon.setTextColor(getColor(R.color.purple))
            viewDataBinding.tvSleepHour.setTextColor(getColor(R.color.purple))

            viewDataBinding.viewTimeWake.background = getDrawable(R.drawable.bg_round_purple)
            viewDataBinding.ivSunAlarm.setImageDrawable(getDrawable(R.drawable.sun_white))
            viewDataBinding.tvWakeMinute.setTextColor(getColor(R.color.white))
            viewDataBinding.tvWakeHour.setTextColor(getColor(R.color.white))
            viewDataBinding.tvWakeColon.setTextColor(getColor(R.color.white))
        }


        viewDataBinding.btnCancelAlarm.setOnClickListener {
            finish()
        }

        viewDataBinding.btnQuit.setOnClickListener {
            val weeks = ArrayList<Boolean>()

            weeks.add(viewDataBinding.checkWeekMon.isChecked)
            weeks.add(viewDataBinding.checkWeekTue.isChecked)
            weeks.add(viewDataBinding.checkWeekWed.isChecked)
            weeks.add(viewDataBinding.checkWeekThu.isChecked)
            weeks.add(viewDataBinding.checkWeekFri.isChecked)
            weeks.add(viewDataBinding.checkWeekSat.isChecked)
            weeks.add(viewDataBinding.checkWeekSun.isChecked)

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, viewDataBinding.tvSleepHour.text.toString().toInt())
            calendar.set(Calendar.MINUTE, viewDataBinding.tvSleepMinute.text.toString().toInt())
            calendar.add(Calendar.MINUTE, -30)

            val alarmVO = AlarmVO(
                weeks,
                viewDataBinding.chckVibrate.isChecked,
                viewDataBinding.checkFive.isChecked,
                viewDataBinding.tvWakeHour.text.toString().toInt(),
                viewDataBinding.tvWakeMinute.text.toString().toInt(),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE)
            )
            alarmVM.updateAlarm(alarmVO)
            alarmUtil.registAlarm(this, alarmVO)

            sharedUtil.saveStringPreference(SharedUtil.ALARM_TEXT, viewDataBinding.etAlarm.text.toString())

            finish()
        }

        viewDataBinding.checkWeekFri.setOnCheckedChangeListener(this)
        viewDataBinding.checkWeekMon.setOnCheckedChangeListener(this)
        viewDataBinding.checkWeekSat.setOnCheckedChangeListener(this)
        viewDataBinding.checkWeekTue.setOnCheckedChangeListener(this)
        viewDataBinding.checkWeekSun.setOnCheckedChangeListener(this)
        viewDataBinding.checkWeekThu.setOnCheckedChangeListener(this)
        viewDataBinding.checkWeekWed.setOnCheckedChangeListener(this)


    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked) {
            buttonView?.setTextColor(resources.getColor(R.color.purple, null))
        } else {
            buttonView?.setTextColor(resources.getColor(R.color.gray_light, null))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getAlarm()?.size == 0) {
            Log.e(TAG, "alarm null")
            insertAlarm()
        }

        Log.e(TAG, getAlarm().toString())

        initStartView()
        initDataBinding()
        initAfterBinding()
    }
}
