package com.nexters.zaza.ui.alarm.usecase

import com.nexters.zaza.ui.alarm.data.vo.AlarmVO
import com.nexters.zaza.ui.alarm.repository.AlarmRepository
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable

class GetAlarm (val alarmRepository: AlarmRepository, val scheduler: Scheduler){
    var disposable: Disposable? = null
    operator fun invoke(
        success: (alarm: AlarmVO) -> Unit,
        error: (throwable: Throwable) -> Unit
    ){
        disposable = alarmRepository.getAlarm(
            scheduler = scheduler,
            success = success,
            error =  error
        )
    }

    fun cancel() {
        disposable?.dispose()
        disposable = null
    }

}