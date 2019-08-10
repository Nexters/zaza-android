package com.teamnexters.zaza.util.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.time.DayOfWeek
import java.util.*

class AlarmUtil {
    fun registAlarm(context:Context, weeks: Array<Boolean>, isVibrate: Boolean, isOneMore: Boolean, sleepTime: Date, wakeTime: Date){

        val intent = Intent(context, AlarmReceiver()::class.java)
        intent.putExtra("vibrate", isVibrate)
        intent.putExtra("oneMore", isOneMore)

        val alarmManager  = context.getSystemService(Context.ALARM_SERVICE)
        for(i in weeks.indices){
            val calendar = Calendar.getInstance()
            var code = 0
            //Sun = 1, Mon = 2 ... Sat = 7
            when(i){
                6 -> code = Calendar.SUNDAY
                0 -> code = Calendar.MONDAY
                1 -> code = Calendar.TUESDAY
                2 -> code = Calendar.WEDNESDAY
                3 -> code = Calendar.THURSDAY
                4 -> code = Calendar.FRIDAY
                5 -> code = Calendar.SATURDAY
            }
            /**
             * 취침 알람 rquestId는 Calendar의 날짜 변수로 설정하고 기상 알람은 *2로 설정한다
             */
            cancleAlarm(code, context)
            cancleAlarm(code*2, context);

            if(weeks[i]) {
                if (calendar.get(Calendar.DAY_OF_WEEK) == code && calendar.after(sleepTime)) {
                    calendar.add(Calendar.DATE, 7);
                } else {
                    if(calendar.get(Calendar.DAY_OF_WEEK) <= code){
                        calendar.add(Calendar.DATE, code - calendar.get(Calendar.DAY_OF_WEEK));
                    } else{
                        calendar.add(Calendar.DATE, (7 - calendar.get(Calendar.DAY_OF_WEEK)) + code);
                    }
                }

                if (sleepTime.before(wakeTime)) {
                    /**
                     * 같은 하루에 기상 & 취침 알람 설정
                     */


                } else {

                    /**
                     * 다음 날 기상 알람 설정
                     */

                }
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