package com.teamnexters.zaza.ui.alarm.data

import com.teamnexters.zaza.ui.alarm.data.vo.AlarmRealm
import com.teamnexters.zaza.ui.alarm.data.vo.AlarmVO
import io.reactivex.Single

interface AlarmDataSource {
    fun getAlarmData(): Single<AlarmVO>
}