package com.teamnexters.zaza.ui.alarm

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teamnexters.zaza.R
import com.teamnexters.zaza.databinding.ActivityAlarmBinding
import com.teamnexters.zaza.databinding.DialogAlarmBottomSheetBinding

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

        if(tag.equals("sleep")){
            binding.timepickerCustom.setHour(activityBinding.tvSleepHour.text.toString().toInt())
            binding.timepickerCustom.setMinute(activityBinding.tvSleepMinute.text.toString().toInt())
        } else{
            binding.timepickerCustom.setHour(activityBinding.tvWakeHour.text.toString().toInt())
            binding.timepickerCustom.setMinute(activityBinding.tvWakeMinute.text.toString().toInt())
        }

        binding.btnDialogAlarmCancel.setOnClickListener {
            dismiss()
        }

        binding.btnDialogAlarmSave.setOnClickListener {
            if (tag.equals("sleep")) {
                activityBinding.tvSleepHour.text = String.format("%02d", binding.timepickerCustom.getHour())
                activityBinding.tvSleepMinute.text = String.format("%02d", binding.timepickerCustom.getMinute())
            } else {
                activityBinding.tvWakeHour.text = String.format("%02d", binding.timepickerCustom.getHour())
                activityBinding.tvWakeMinute.text = String.format("%02d", binding.timepickerCustom.getMinute())
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