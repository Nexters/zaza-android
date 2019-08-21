package com.teamnexters.zaza.ui.dream


import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.teamnexters.zaza.R
import kotlinx.android.synthetic.main.activity_dream.*


class CustomDreamDialog(context: Context, okListener: View.OnClickListener) : Dialog(context){

    lateinit var tv_delete_yes:TextView
    lateinit var tv_delete_no:TextView
    var listener: View.OnClickListener = okListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)                   //다이얼로그의 타이틀 바 제거
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))  //다이얼로그 배경 투명
        setContentView(R.layout.dialog_dream_detail_delete)

        tv_delete_yes = findViewById(R.id.tv_delete_yes)
        tv_delete_no = findViewById(R.id.tv_delete_no)

        tv_delete_no.setOnClickListener { cancel() }
        tv_delete_yes.setOnClickListener (listener)
    }

}