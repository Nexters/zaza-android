package com.nexters.zaza.util.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import android.os.PowerManager
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.nexters.zaza.MainActivity
import com.nexters.zaza.ZazaConstant
import com.nexters.zaza.ui.alarm.data.vo.AlarmVO
import com.nexters.zaza.util.NotificationManager
import com.nexters.zaza.util.SharedUtil
import com.nexters.zaza.util.getAlarm
import retrofit2.http.HEAD
import java.util.*
import kotlin.collections.ArrayList

class AlarmReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onReceive(context: Context?, intent: Intent?) {


        Toast.makeText(context, "Receiver start", Toast.LENGTH_SHORT).show()

        val alarmRealm = getAlarm()

        val alarmUtil = AlarmUtil.instance


        val isSleep = intent?.getStringExtra("sleep")
        val isWake = intent?.getStringExtra("wake")
        val state = intent?.getStringExtra("state")

        Log.e("AlarmReceiver", isSleep.toString())
        val intent = Intent(context, MainActivity::class.java)
        val sharedUtil = SharedUtil(context!!)

        if (isSleep.equals("sleep")) {
            alarmRealm?.forEach { a ->
                val weeks = ArrayList<Boolean>()
                a.weeks.forEach { w ->
                    weeks.add(w)
                }
                val alarmVO = AlarmVO(
                    weeks,
                    a.isVibrate,
                    a.isAfterFive,
                    a.wakeUpH,
                    a.wakeUpM,
                    a.sleepH,
                    a.sleepM
                )
                alarmUtil.registAlarm(context, alarmVO)
            }
            if (isSleep.equals("sleep")) {
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.MINUTE, 30)
                alarmUtil.afterThirtyAlarm(context, calendar)
                intent.putExtra("SLEEP_READY","SLEEP_READY")
                val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
                if (powerManager.isInteractive)
                    context.startActivity(intent)
                else
                    NotificationManager.sendNotification(
                        context,
                        3,
                        NotificationManager.Channel.NOTICE,
                        "취침시간 30분 전입니다!",
                        sharedUtil.getStringPreference(SharedUtil.ALARM_TEXT, SharedUtil.DEFAULT_VALUE)
                    )
            } else {
                val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager

                if (powerManager.isInteractive)
                    context.startActivity(intent)
                else
                    NotificationManager.sendNotification(
                        context,
                        3,
                        NotificationManager.Channel.NOTICE,
                        "취침시간입니다!",
                        "잠에 들 준비를 해주세요!"
                    )
            }

            val serviceIntent = Intent(context, RingtoneService::class.java)
            serviceIntent.putExtra("state", state)

            if (isWake.equals("wake")) {
                Toast.makeText(context, "AlarmStart", Toast.LENGTH_SHORT).show()
                val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                val intent = Intent()
                intent.action = ZazaConstant.BC_ALARM_TIME
                context.sendBroadcast(intent)

                when (audioManager.ringerMode) {
                    AudioManager.RINGER_MODE_NORMAL ->
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            context.startForegroundService(serviceIntent)
                        } else {
                            context.startService(serviceIntent)
                        }
                }
                if (state.equals("alarmOn")) {
                    Toast.makeText(context, "AlarmStart", Toast.LENGTH_SHORT).show()
                    val timings = longArrayOf(100, 100, 400, 200, 400)
                    val amplitudes = intArrayOf(0, 50, 0, 100, 0, 50, 0, 150)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createWaveform(timings, amplitudes, 0))
                    } else {
                        vibrator.vibrate(timings, 0)
                    }
                } else
                    vibrator.cancel()
            }
//        val intent = Intent(context, MainActivity::class.java)
//        context.startActivity(intent)

        }
    }
}