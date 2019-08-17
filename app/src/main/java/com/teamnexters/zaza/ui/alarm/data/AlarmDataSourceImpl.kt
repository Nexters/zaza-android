package com.teamnexters.zaza.ui.alarm.data

import android.util.Log
import com.teamnexters.zaza.ui.alarm.data.vo.AlarmRealm
import com.teamnexters.zaza.ui.alarm.data.vo.AlarmVO
import io.reactivex.Single
import com.teamnexters.zaza.util.getAlarm
import io.realm.RealmList


class AlarmDataSourceImpl :AlarmDataSource{

    override fun getAlarmData(): Single<AlarmVO> {
        return Single.create<AlarmVO>{
            val alarm = getAlarm()
            alarm?.forEach { a ->
                val weeks =  ArrayList<Boolean>()
                Log.e("getAlarm", a.weeks.toString())
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