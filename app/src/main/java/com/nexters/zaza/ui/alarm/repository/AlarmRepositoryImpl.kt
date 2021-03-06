package com.nexters.zaza.ui.alarm.repository
import com.nexters.zaza.ui.alarm.data.AlarmDataSourceImpl
import com.nexters.zaza.ui.alarm.data.vo.AlarmVO
import com.nexters.zaza.util.updateAlarmRealm
import io.reactivex.Scheduler
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

    override fun updateAlarm(alarmVO: AlarmVO) {
        updateAlarmRealm(alarmVO)
    }


}