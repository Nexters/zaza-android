package com.teamnexters.zaza.ui.alarm.repository

import com.teamnexters.zaza.ui.alarm.data.AlarmDataSource
import com.teamnexters.zaza.ui.alarm.data.AlarmDataSourceImpl
import com.teamnexters.zaza.ui.alarm.data.vo.AlarmVO
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class AlarmRepositoryImpl : AlarmRepository {
    val alarmDataSource = AlarmDataSourceImpl()
    override fun getAlarm(
        scheduler: Scheduler,
        success: (alarm: AlarmVO) -> Unit,
        error: (throwable: Throwable) -> Unit
    ): Disposable {
        return alarmDataSource.getAlarmData()
            .subscribeOn(scheduler)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ alarm ->
                success(alarm)

            })
            {
                t ->
                error(t)
            }
    }


}