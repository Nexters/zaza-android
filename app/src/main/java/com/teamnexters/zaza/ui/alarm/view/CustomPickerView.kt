package com.teamnexters.zaza.ui.alarm.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.NumberPicker

class CustomPickerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : NumberPicker(context, attrs, defStyleAttr){

    override fun setBaselineAligned(baselineAligned: Boolean) {
        super.setBaselineAligned(false)
    }

    override fun setTextAlignment(textAlignment: Int) {
        super.setTextAlignment(textAlignment)
    }

    override fun addView(child: View?) {
        super.addView(child)
    }

    override fun addView(child: View?, index: Int) {
        super.addView(child, index)
    }

    override fun addView(child: View?, width: Int, height: Int) {
        super.addView(child, width, height)
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        super.addView(child, params)
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        super.addView(child, index, params)
    }

    fun updateView(view: View){

    }
}