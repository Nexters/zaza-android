package com.teamnexters.zaza.ui.alarm.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.NumberPicker

class CustomTimePickerView@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr){

    var hourPicker: NumberPicker
    init {
        hourPicker = CustomPickerView(context)
        hourPicker.isBaselineAligned = false

    }


}