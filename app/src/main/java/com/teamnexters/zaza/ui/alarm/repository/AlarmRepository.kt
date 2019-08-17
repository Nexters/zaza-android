package com.teamnexters.zaza.ui.alarm.repository

import com.teamnexters.zaza.ui.alarm.data.vo.AlarmVO
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.Disposable

interface AlarmRepository {
    fun getAlarm(scheduler: Scheduler,
                 success: (alarm: AlarmVO) ->Unit,
                 error: (throwable: Throwable) -> Unit
    ): Disposable

    fun updateAlarm(alarmVO: AlarmVO)
}
