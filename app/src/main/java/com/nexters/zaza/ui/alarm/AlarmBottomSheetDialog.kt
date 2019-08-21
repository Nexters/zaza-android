package com.nexters.zaza.ui.alarm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nexters.zaza.R
import com.nexters.zaza.databinding.ActivityAlarmBinding
import com.nexters.zaza.databinding.DialogAlarmBottomSheetBinding

class AlarmBottomSheetDialog : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var binding: DialogAlarmBottomSheetBinding
    private lateinit var activityBinding: ActivityAlarmBinding
    var hour = 0
    var minute = 0;
    private var layoutRes = getLayoutRes()
    override fun onClick(v: View?) {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        if (layoutRes > -1) {
            return onDatabinding(inflater, container)
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun subscribeUi() {

        binding.tpAlamr.setIs24HourView(true)
        binding.tpAlamr.setOnTimeChangedListener(TimePicker.OnTimeChangedListener { timePicker: TimePicker, h: Int, m: Int ->

        })

        binding.btnDialogAlarmCancel.setOnClickListener {
            dismiss()
        }

        binding.btnDialogAlarmSave.setOnClickListener {
            if (tag.equals("sleep")) {
                activityBinding.tvSleepHour.text = String.format("%02d", binding.tpAlamr.hour)
                activityBinding.tvSleepMinute.text = String.format("%02d", binding.tpAlamr.minute)
            } else {
                activityBinding.tvWakeHour.text = String.format("%02d", binding.tpAlamr.hour)
                activityBinding.tvWakeMinute.text = String.format("%02d", binding.tpAlamr.minute)
            }

            val sh = activityBinding.tvSleepHour.text.toString().toInt()
            val sm = activityBinding.tvSleepMinute.text.toString().toInt()
            var wh = activityBinding.tvWakeHour.text.toString().toInt()
            val wm = activityBinding.tvWakeMinute.text.toString().toInt()

            if((sh > wh) ||( (sh == wh) && (sm >= wm)))
                wh +=24

            val t = Math.abs(sh-wh)

            activityBinding.tvSleepTime.text = "+$t"
            dismiss()
        }


    }

    fun setActivityBinding(alarmBinding: ActivityAlarmBinding) {
        activityBinding = alarmBinding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeUi()
    }

    fun onDatabinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.alarmDialogView = this
        return binding.root
    }



    fun getLayoutRes(): Int {
        return R.layout.dialog_alarm_bottom_sheet
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if(dialog != null && dialog!!.isShowing)
            return
        super.show(manager, tag)
    }
}