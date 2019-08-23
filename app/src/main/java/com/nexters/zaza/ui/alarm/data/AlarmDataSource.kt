package com.nexters.zaza.ui.alarm.data

import com.nexters.zaza.ui.alarm.data.vo.AlarmVO
import io.reactivex.Single

interface AlarmDataSource {
    fun getAlarmData(): Single<AlarmVO>
}