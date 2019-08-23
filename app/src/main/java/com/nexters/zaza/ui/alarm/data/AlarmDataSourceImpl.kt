package com.nexters.zaza.ui.alarm.data

import com.nexters.zaza.ui.alarm.data.vo.AlarmVO
import io.reactivex.Single
import com.nexters.zaza.util.getAlarm


class AlarmDataSourceImpl :AlarmDataSource{

    override fun getAlarmData(): Single<AlarmVO> {
        return Single.create<AlarmVO>{
            val alarm = getAlarm()
            alarm?.forEach { a ->
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
                it.onSuccess(alarmVO)
            }
        }
    }

}