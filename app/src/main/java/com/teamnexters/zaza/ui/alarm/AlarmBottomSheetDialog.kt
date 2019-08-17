package com.teamnexters.zaza.ui.alarm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
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

    companion object {

        private var _instance: AlarmBottomSheetDialog? = null

        val instance: AlarmBottomSheetDialog
            get() {
                if (_instance == null)
                    _instance = AlarmBottomSheetDialog()
                return _instance as AlarmBottomSheetDialog
            }
    }


}