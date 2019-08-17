package com.teamnexters.zaza.util.alarm

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.teamnexters.zaza.MainActivity
import com.teamnexters.zaza.ui.alarm.data.vo.AlarmVO
import com.teamnexters.zaza.util.getAlarm

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val builder = NotificationCompat.Builder(context!!, "1")
            .setContentTitle("Alarm")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(context)
//        notificationManager.notify(22, builder.build())

        val alarmRealm = getAlarm()

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
            val alarmUtil = AlarmUtil.instance
            alarmUtil.registAlarm(context, alarmVO)
        }


        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)

    }
}