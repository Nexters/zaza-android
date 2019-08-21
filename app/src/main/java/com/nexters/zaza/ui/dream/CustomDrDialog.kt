package com.nexters.zaza.ui.dream


import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import com.nexters.zaza.R


class CustomDrDialog(context: Context, okListener: View.OnClickListener) : Dialog(context){

    lateinit var btn_yes:Button
    lateinit var btn_no:Button
    var listener: View.OnClickListener = okListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)                   //다이얼로그의 타이틀 바 제거
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))  //다이얼로그 배경 투명
        setContentView(R.layout.dialog_dream_detail_delete)

        btn_yes = findViewById(R.id.btn_yes)
        btn_no = findViewById(R.id.btn_no)

        btn_no.setOnClickListener { cancel() }
        btn_yes.setOnClickListener (listener)
    }

}