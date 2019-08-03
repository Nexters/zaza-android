package com.teamnexters.zaza.ui.alarm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teamnexters.zaza.R
import com.teamnexters.zaza.databinding.ActivityAlarmBinding
import com.teamnexters.zaza.databinding.DialogAlarmBottomSheetBinding

class AlarmBottomSheetDialog : BottomSheetDialogFragment(), View.OnClickListener{

    private lateinit var binding: DialogAlarmBottomSheetBinding
    private var layoutRes = getLayoutRes()

    fun getInstance(): AlarmBottomSheetDialog{
        return AlarmBottomSheetDialog()
    }

    override fun onClick(v: View?) {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        if(layoutRes > -1){
            return onDatabinding(inflater, container)
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun subscribeUi(){

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeUi()
    }

    fun onDatabinding(inflater: LayoutInflater, container: ViewGroup?) : View{
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.alarmDialogView = this
        return binding.root
    }

    fun getLayoutRes():Int{
        return R.layout.dialog_alarm_bottom_sheet
    }



}