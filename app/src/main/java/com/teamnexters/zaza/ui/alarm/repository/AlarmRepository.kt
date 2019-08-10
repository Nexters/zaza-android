package com.teamnexters.zaza.ui.alarm.repository

import com.teamnexters.zaza.ui.alarm.vo.AlarmVO
import io.reactivex.Single

interface AlarmRepository {
    fun getWeeks(): Single<List<Boolean>>
}
