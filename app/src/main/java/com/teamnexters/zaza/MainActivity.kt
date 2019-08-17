package com.teamnexters.zaza

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.teamnexters.zaza.base.BaseActivity
import com.teamnexters.zaza.databinding.ActivityMainBinding
import com.teamnexters.zaza.sample.SampleViewModel
import com.teamnexters.zaza.sample.firebase.ImageActivity
import com.teamnexters.zaza.ui.dream.DreamActivity
import com.teamnexters.zaza.ui.main.SleepReadyDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class MainActivity : BaseActivity<ActivityMainBinding>(), View.OnClickListener, SensorEventListener {
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        //
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        Log.v("Main","\n* * * wow x: ${p0!!.values[0]} // y : ${p0!!.values[1]} // z : ${p0!!.values[2]}")
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_main

    lateinit var sampleViewModel: SampleViewModel
    lateinit var sensorManager: SensorManager
    lateinit var sensorEventListener: SensorEventListener
    lateinit var gyro: Sensor
    var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        image_main_dream.setOnClickListener(this)
        text_main_dream.setOnClickListener(this)
        text_main_logo.setOnClickListener(this)
        image_main_onoff.setOnClickListener(this)

        sampleViewModel = ViewModelProviders.of(this).get(SampleViewModel::class.java)

        // UI업데이트를 받는 observer를 만든다.
        val nameObserver = Observer<String> { newName ->
            // TextView의 UI가 업데이트 됐을 경우
//            text_sample.text = newName
        }

        // 이 활동을 LifecycleOwner 및 옵저버로 전달하여 LiveData를 관찰한다.
        sampleViewModel.nameTag.observe(this, nameObserver)

        @SuppressLint("HandlerLeak")
        handler = object : Handler() {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                val cal = Calendar.getInstance()

                val sdf = SimpleDateFormat("hh : mm")
                val strTime = sdf.format(cal.time)
                text_main_time.text = strTime
            }
        }

        thread(start = true) {
            while (true) {
                Thread.sleep(1000)
                handler.sendEmptyMessage(0)
            }
        }

        // 센서 매니저 생성
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        // 자이로 센서 등록
        sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY).let {
            this.gyro = it
        }
        // 센서 이벤트 리스너
        sensorEventListener = object : SensorEventListener {
            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                //
            }

            override fun onSensorChanged(p0: SensorEvent?) {
                when (p0!!.sensor.type) {
                    Sensor.TYPE_GRAVITY -> {
                        Log.v("Main","\n* * * x: ${p0!!.values[0]} // y : ${p0!!.values[1]} // z : ${p0!!.values[2]}")
                        if (p0!!.values[2] < 0) {
                            // 휴대폰 뒤집었을 때
                        } else {
                            // 휴대폰 원상태
                        }
                    }
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(sensorEventListener, gyro, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorEventListener)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            image_main_dream, text_main_dream -> {
                val sampleIntent = Intent(this, DreamActivity::class.java)
                startActivity(sampleIntent)
            }
            text_main_logo -> {
                val nextIntent = Intent(this, ImageActivity::class.java)
                startActivity(nextIntent)
            }
            image_main_onoff -> {
                Log.v("Main", "image_main_onoff")
                SleepReadyDialog.getInstance().show(supportFragmentManager, "SleepReadyDialog")
            }
        }
    }
}
