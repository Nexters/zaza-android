package com.nexters.zaza.ui.alarm.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.NumberPicker
import com.nexters.zaza.R
import kotlin.math.min

class CustomTimePickerView@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr){

    lateinit var hourPicker: NumberPicker
    lateinit var minutePicker: NumberPicker
    init {

        initView()
    }

    fun initView(){

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.timepicker_layout, this, false)
        addView(v)
        hourPicker = v.findViewById(R.id.picker_hour)
        hourPicker.maxValue = 23
        hourPicker.minValue = 0
        minutePicker = v.findViewById(R.id.picker_minute)
        minutePicker.maxValue = 59
        minutePicker.minValue = 0


    }

    fun getHour(): Int{
        return hourPicker.value
    }

    fun getMinute(): Int{
        return minutePicker.value
    }


}