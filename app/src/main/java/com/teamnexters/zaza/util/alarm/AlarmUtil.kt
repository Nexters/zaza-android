package com.teamnexters.zaza.util.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*

class AlarmUtil {


    fun registAlarm(context:Context, weeks: Array<Boolean>, isVibrate: Boolean, isOneMore: Boolean){

        val intent = Intent(context, AlarmReceiver()::class.java)
        intent.putExtra("vibrate", isVibrate)
        intent.putExtra("oneMore", isOneMore)

        for(i in weeks.indices){
            var code = 0
            when(i){
                0 -> code = Calendar.MONDAY
                1 -> code = Calendar.TUESDAY
                2 -> code = Calendar.WEDNESDAY
                3 -> code = Calendar.THURSDAY
                4 -> code = Calendar.FRIDAY
                5 -> code = Calendar.SATURDAY
                6 -> code = Calendar.SUNDAY
            }

            cancleAlarm(code, context)
            if(weeks[i]){

            }
        }

    }

    fun setTriggerTime(){

    }

    fun getPendingIntent(intent: Intent, requestId :Int, context: Context): PendingIntent{
        /**
         * requestCode Calendar로 처리
         */
        val pendingIntent = PendingIntent.getBroadcast(context, requestId, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        return pendingIntent
    }

    fun cancleAlarm(requestId: Int, context:Context){
        val intent = Intent(context, AlarmReceiver().javaClass)
        val pendingIntent = getPendingIntent(intent, requestId, context)
        if(pendingIntent != null) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            pendingIntent.cancel()
        }

    }

    companion object {

        private var _instance: AlarmUtil? = null

        val instance: AlarmUtil
            get() {
                if (_instance == null)
                    _instance = AlarmUtil()
                return _instance as AlarmUtil
            }
    }
}