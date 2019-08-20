package com.teamnexters.zaza

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.content.SharedPreferences
import android.os.*
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.teamnexters.zaza.base.BaseActivity
import com.teamnexters.zaza.databinding.ActivityMainBinding
import com.teamnexters.zaza.sample.SampleViewModel
import com.teamnexters.zaza.sample.firebase.ImageActivity
import com.teamnexters.zaza.ui.dream.DreamActivity
import com.teamnexters.zaza.ui.main.CancelSleepDialog
import com.teamnexters.zaza.ui.main.SleepReadyDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread


class MainActivity : BaseActivity<ActivityMainBinding>(), View.OnClickListener {
    override val layoutResourceId: Int
        get() = R.layout.activity_main

    val COUNT_TIME: Long = 11 * 1000
    val COUNT_INTERVAL: Long = 1000

    lateinit var sampleViewModel: SampleViewModel
    lateinit var sensorManager: SensorManager
    lateinit var sensorEventListener: SensorEventListener
    lateinit var gyro: Sensor
    lateinit var countDownTimer: CountDownTimer
    lateinit var cancelSleepDialog: CancelSleepDialog
    lateinit var runnable: Runnable

    var openDialogStatus = false
    var isSleepMode = false
    var isCountDown = false
    var handler = Handler()
    var sleepReadyDialog = SleepReadyDialog.getInstance()
    var countTime = 10

    // Stop watch var
    var msTime = 0L
    var startTime = 0L
    var timeBuff = 0L
    var updateTime = 0L
    var sec = 0
    var min = 0
    var milliSec = 0
    var swHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref : SharedPreferences = getSharedPreferences("APP_INFO", Context.MODE_PRIVATE)
        val appUuid = sharedPref.getString("UUID", null)
        if (appUuid == null) {
            Log.d("MAIN", "The uuid will be generated.")
            val editor = sharedPref.edit()
            editor.putString("UUID", UUID.randomUUID().toString())
            editor.apply()
        } else {
            Log.d("MAIN", "The uuid already has been generated.")
            Log.d("MAIN", appUuid)
        }

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

        countDownTimer = object: CountDownTimer(COUNT_TIME, COUNT_INTERVAL) {
            override fun onFinish() {
                cancel()
                finishSleepMode()
                if (cancelSleepDialog.isAdded) {
                    cancelSleepDialog.dismissAllowingStateLoss()
                }
            }

            override fun onTick(p0: Long) {
//                Log.v("Main", "* * * ${p0}")
                text_main_sleep_guide.text = getString(R.string.sleep_mode_bottom_guide, countTime)
                countTime = countTime - 1
            }
        }
        hideCountDown()

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
//                        Log.v("Main","\n* * * x: ${p0!!.values[0]} // y : ${p0!!.values[1]} // z : ${p0!!.values[2]}")
                        if (openDialogStatus && p0!!.values[2] < 0) {
                            // 휴대폰 뒤집었을 때
                            onSleepMode()
                            isSleepMode = true
                            hideCountDown()
                        } else {
                            // 수면모드에서 폰을 원상태로 돌릴 경우
                            if (!isCountDown) {
                                showCountDown()
                            }
                        }
                    }
                }
            }
        }

        cancelSleepDialog = CancelSleepDialog.getInstance {
            if (it) {
                finishSleepMode()
            } else {
                cancelSleepDialog.dismissAllowingStateLoss()
            }
        }

        stopWatch()
    }

    fun stopWatch() {
        runnable = object : Runnable {
            override fun run() {
                msTime = SystemClock.uptimeMillis() - startTime
                updateTime = timeBuff + msTime
                sec = (updateTime / 1000).toInt()
                min = sec / 60
                milliSec = (updateTime % 1000).toInt()
                text_main_stop_swatch.text = "${min}:${sec}:${milliSec}"
                swHandler.postDelayed(this, 0)
            }
        }
    }

    fun onSleepMode() {
        sleepReadyDialog.dismissAllowingStateLoss()

        text_main_time.visibility = View.GONE
        text_main_stop_swatch.visibility = View.VISIBLE

        layout_main_root.background = ContextCompat.getDrawable(this, R.drawable.bg_gradient_black)
        image_main_alarm.visibility = View.GONE
        text_main_alarm.visibility = View.GONE
        image_main_dream.visibility = View.GONE
        text_main_dream.visibility = View.GONE
        image_main_onoff.setImageResource(R.drawable.off_switch_btn)
        text_main_logo.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
        window.statusBarColor = ContextCompat.getColor(this, R.color.gray_dark)

        // Start StopWatch
        startTime = SystemClock.uptimeMillis()
        swHandler.postDelayed(runnable, 0)

    }

    fun finishSleepMode() {
        hideCountDown()

        text_main_time.visibility = View.VISIBLE
        text_main_stop_swatch.visibility = View.GONE

        layout_main_root.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        image_main_alarm.visibility = View.VISIBLE
        text_main_alarm.visibility = View.VISIBLE
        image_main_dream.visibility = View.VISIBLE
        text_main_dream.visibility = View.VISIBLE
        image_main_onoff.setImageResource(R.drawable.on_switch)
        text_main_logo.setTextColor(ContextCompat.getColor(this, R.color.gray))
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        openDialogStatus = false
        isSleepMode = false
    }

    fun showCountDown() {
        isCountDown = true
        countDownTimer.start()
        text_main_sleep_guide.visibility = View.VISIBLE
    }

    fun hideCountDown() {
        countDownTimer.cancel()
        text_main_sleep_guide.visibility = View.GONE
        isCountDown = false
        countTime = 10 // reset
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
                if (isSleepMode) {
                    cancelSleepDialog.show(supportFragmentManager, "CancelSleepDialog")
                } else {
                    openDialogStatus = true
                    sleepReadyDialog.show(supportFragmentManager, "SleepReadyDialog")
                }
            }
        }
    }
}
