package com.nexters.zaza.ui.dream


import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import com.nexters.zaza.R


class CustomDreamDialog(context: Context, okListener: View.OnClickListener) : Dialog(context) {

    lateinit var tv_delete_yes: TextView
    lateinit var tv_delete_no: TextView
    lateinit var tv_dialog_content: TextView
    var textYes:String = "취소"
    var textNo:String = "삭제"
    var textContent:String = "꿈을 삭제할까요?\\n삭제한 꿈은 복구할 수 없습니다."

    var listener: View.OnClickListener = okListener
    var cancelListener: View.OnClickListener = View.OnClickListener { cancel() }
    constructor(context: Context, okListener: View.OnClickListener, cancelListener:View.OnClickListener, yes:String, no:String, content: String) : this(context,okListener) {
        textYes = yes
        textNo = no
        textContent = content
        listener = okListener
        this.cancelListener = cancelListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)                   //다이얼로그의 타이틀 바 제거
//        window.setBackgroundDrawable(ColorDrawable(Color.WHITE))  //다이얼로그 배경 하양
        setContentView(R.layout.dialog_dream_detail_delete)

        window.setBackgroundDrawableResource(R.drawable.bg_delete_dream)

        tv_delete_yes = findViewById(R.id.tv_delete_yes)
        tv_delete_no = findViewById(R.id.tv_delete_no)
        tv_dialog_content = findViewById(R.id.tv_content_dialog)

        tv_delete_yes.text = textYes
        tv_delete_no.text = textNo
        tv_dialog_content.text = textContent

        tv_delete_no.setOnClickListener(cancelListener)
        tv_delete_yes.setOnClickListener(listener)
    }



}