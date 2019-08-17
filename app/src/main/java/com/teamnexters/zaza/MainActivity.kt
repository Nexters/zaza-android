package com.teamnexters.zaza

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.teamnexters.zaza.base.BaseActivity
import com.teamnexters.zaza.databinding.ActivityMainBinding
import com.teamnexters.zaza.sample.SampleViewModel
import com.teamnexters.zaza.sample.firebase.DatabaseActivity
import com.teamnexters.zaza.sample.firebase.ImageActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_main

    lateinit var sampleViewModel: SampleViewModel
    var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sampleViewModel = ViewModelProviders.of(this).get(SampleViewModel::class.java)

        // UI업데이트를 받는 observer를 만든다.
        val nameObserver = Observer<String> { newName ->
            // TextView의 UI가 업데이트 됐을 경우
//            text_sample.text = newName
        }

        // 이 활동을 LifecycleOwner 및 옵저버로 전달하여 LiveData를 관찰한다.
        sampleViewModel.nameTag.observe(this, nameObserver)

        button_sample.setOnClickListener {
//            sampleViewModel.getName().value = "Sample!!!!"
//            sampleViewModel.nameTag.value = "Sample!!!!"
            val nextIntent = Intent(this, ImageActivity::class.java)
            startActivity(nextIntent)
        }
        button_dream.setOnClickListener {
//            val sampleIntent = Intent(this, DreamActivity::class.java)
//            startActivity(sampleIntent)
        }

        @SuppressLint("HandlerLeak")
        handler = object : Handler() {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                val cal = Calendar.getInstance()

                val sdf = SimpleDateFormat("hh:mm")
                val strTime = sdf.format(cal.time)
                text_main_time.text = strTime
            }
        }

        thread(start = true) {
            while (true) {
                Thread.sleep(1000)
                handler?.sendEmptyMessage(0)
            }
        }

    }
}
