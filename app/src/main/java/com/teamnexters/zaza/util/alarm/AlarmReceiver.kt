package com.teamnexters.zaza.util.alarm

import android.app.NotificationManager
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
import com.teamnexters.zaza.MainActivity
import com.teamnexters.zaza.ui.alarm.data.vo.AlarmVO
import com.teamnexters.zaza.util.getAlarm
import java.util.*
import kotlin.collections.ArrayList

class AlarmReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onReceive(context: Context?, intent: Intent?) {

        val builder = NotificationCompat.Builder(context!!, "1")
            .setContentTitle("Alarm")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(context)
//        notificationManager.notify(22, builder.build())

        val alarmRealm = getAlarm()

        val alarmUtil = AlarmUtil.instance
        alarmRealm?.forEach { a ->
            val weeks =  ArrayList<Boolean>()
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



        val isSleep = intent?.getStringExtra("sleep")
        val isWake = intent?.getStringExtra("wake")
        val state = intent?.getStringExtra("state")
        Log.e("AlarmReceiver", isSleep.toString())
        if(isSleep.equals("sleep")){
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MINUTE, 30)
            alarmUtil.afterThirtyAlarm(context, calendar)
        }

        val serviceIntent = Intent(context, RingtoneService::class.java)
        serviceIntent.putExtra("state", state)

        if(isWake.equals("wake")){
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            when(audioManager.ringerMode){
               AudioManager.RINGER_MODE_NORMAL->
                   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                       context.startForegroundService(serviceIntent)
                   } else{
                       context.startService(serviceIntent)
                   }
            }
            if(state.equals("alarmOn")) {
                val timings = longArrayOf(100, 100, 400, 200, 400)
                val amplitudes = intArrayOf(0, 50, 0, 100, 0, 50, 0, 150)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createWaveform(timings, amplitudes, 0))
                } else{
                    vibrator.vibrate(timings,0)
                }
            }
            else
                vibrator.cancel()
        }



        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }
}