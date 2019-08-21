package com.nexters.zaza.ui.alarm.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.NumberPicker
import androidx.core.content.res.ResourcesCompat
import com.nexters.zaza.R

class CustomPickerView : NumberPicker {


    val typeFace: Typeface

    constructor(context: Context) : super(context) {
        wrapSelectorWheel = true
        dividerDrawable = null
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    init {

//        typeFace = Typeface.createFromAsset(context.assets, "questrial_regular.ttf")
        typeFace = ResourcesCompat.getFont(context, R.font.questrial_regular)!!
    }


    override fun addView(child: View?) {
        super.addView(child)
        updateView(child)
    }

    override fun addView(child: View?, index: Int) {
        super.addView(child, index)
        updateView(child)
    }

    override fun addView(child: View?, width: Int, height: Int) {
        super.addView(child, width, height)
        updateView(child)
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        super.addView(child, params)
        updateView(child)
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        super.addView(child, index, params)
        updateView(child)
    }

    @SuppressLint("ResourceType")
    fun updateView(view: View?) {

        if (view is EditText) {
            view.setTypeface(typeFace)
            view.setTextSize(31F)
            view.setTextColor(resources.getInteger(R.color.purple_dark))
        }
    }
}