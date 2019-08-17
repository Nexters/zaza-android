package com.teamnexters.zaza.util.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.teamnexters.zaza.ui.alarm.data.vo.AlarmRealm
import com.teamnexters.zaza.ui.alarm.data.vo.AlarmVO
import java.util.*

class AlarmUtil {
    fun registAlarm(context: Context, alarmRealm: AlarmVO) {

        val sleepTime = getTime(alarmRealm.sleepH, alarmRealm.sleepM)
        val wakeTime = getTime(alarmRealm.wakeUpH, alarmRealm.wakeUpM)

        val intent = Intent(context, AlarmReceiver()::class.java)
        intent.putExtra("vibrate", alarmRealm.isVibrate)
        intent.putExtra("oneMore", alarmRealm.isAfterFive)
        intent.putExtra("isRingtone", false)

        for (i in alarmRealm.weeks.indices) {
            var code = 0
            //Sun = 1, Mon = 2 ... Sat = 7
            when (i) {
                6 -> code = Calendar.SUNDAY
                0 -> code = Calendar.MONDAY
                1 -> code = Calendar.TUESDAY
                2 -> code = Calendar.WEDNESDAY
                3 -> code = Calendar.THURSDAY
                4 -> code = Calendar.FRIDAY
                5 -> code = Calendar.SATURDAY
            }
            /**
             * 취침 알람 rquestId는 Calendar의 날짜 변수로 설정하고 기상 알람은 *10로 설정한다
             */
            cancleAlarm(code, context)
            cancleAlarm(code * 2, context);

            if (alarmRealm.weeks[i]) {
                val calendar = Calendar.getInstance()
                if (calendar.get(Calendar.DAY_OF_WEEK) == code && (calendar.get(Calendar.HOUR) >= alarmRealm.sleepH) && (calendar.get(Calendar.MINUTE)>= alarmRealm.sleepM)) {
                    calendar.add(Calendar.DATE, 7);
                } else {
                    if (calendar.get(Calendar.DAY_OF_WEEK) <= code) {
                        calendar.add(Calendar.DATE, code - calendar.get(Calendar.DAY_OF_WEEK));
                    } else {
                        calendar.add(Calendar.DATE, (7 - calendar.get(Calendar.DAY_OF_WEEK)) + code);
                    }
                }

                val c = Calendar.getInstance()
                c.timeInMillis = sleepTime.time
                calendar.set(Calendar.HOUR, c.get(Calendar.HOUR))
                calendar.set(Calendar.MINUTE, c.get(Calendar.MINUTE))
                Log.e(
                    "AlarmUtil:: sleep = ", calendar.get(Calendar.YEAR).toString() + ","
                            + calendar.get(Calendar.MONTH) + ","
                            + calendar.get(Calendar.DAY_OF_MONTH) + ","
                            + calendar.get(Calendar.HOUR) + "," +
                            calendar.get(Calendar.MINUTE)
                )
                setTriggerTime(context, calendar, intent, code)

                c.timeInMillis = wakeTime.time
                calendar.set(Calendar.HOUR, c.get(Calendar.HOUR))
                calendar.set(Calendar.MINUTE, c.get(Calendar.MINUTE))
                if ((alarmRealm.sleepH >= alarmRealm.wakeUpH) && alarmRealm.sleepM >= alarmRealm.wakeUpM) {
                    /**
                     * 다음 날 기상 알람 설정
                     */
                    calendar.add(Calendar.DATE, 1)
                }
                setTriggerTime(context, calendar, intent, code*10)
                Log.e(
                    "AlarmUtil:: wake = ", calendar.get(Calendar.YEAR).toString() + ","
                            + calendar.get(Calendar.MONTH) + ","
                            + calendar.get(Calendar.DAY_OF_MONTH) + ","
                            + calendar.get(Calendar.HOUR) + "," +
                            calendar.get(Calendar.MINUTE)
                )
            }

        }

    }

    fun getTime(hour: Int, minute: Int): Date {
        val calendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR, hour)
        calendar.set(Calendar.MINUTE, minute)

        return calendar.time
    }

    fun setTriggerTime(context: Context, time: Calendar, intent: Intent, requestId: Int) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = getPendingIntent(intent, requestId, context)

        alarmManager.set(AlarmManager.RTC_WAKEUP, time.timeInMillis, pendingIntent)
    }

    fun getPendingIntent(intent: Intent, requestId: Int, context: Context): PendingIntent {
        /**
         * requestCode Calendar로 처리
         */
        val pendingIntent = PendingIntent.getBroadcast(context, requestId, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        return pendingIntent
    }

    fun cancleAlarm(requestId: Int, context: Context) {
        val intent = Intent(context, AlarmReceiver().javaClass)
        val pendingIntent = getPendingIntent(intent, requestId, context)
        if (pendingIntent != null) {
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