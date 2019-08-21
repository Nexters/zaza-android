package com.nexters.zaza.ui.alarm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nexters.zaza.base.BaseViewModel
import com.nexters.zaza.ui.alarm.repository.AlarmRepositoryImpl
import com.nexters.zaza.ui.alarm.data.vo.AlarmVO
import com.nexters.zaza.ui.alarm.usecase.GetAlarm
import io.reactivex.schedulers.Schedulers

class AlarmViewModel: BaseViewModel() {

    val TAG = "AlarmViewMoel"
    val mutableAlarmData = MutableLiveData<AlarmVO>()
    private val repository =  AlarmRepositoryImpl()

    val useCaseAlarm: GetAlarm
    init {
        useCaseAlarm = GetAlarm(repository, Schedulers.io())
        getAlarm()
    }

    fun getAlarm(){
        useCaseAlarm(
            success = { alarm ->
                mutableAlarmData.value = alarm
                Log.e(TAG, mutableAlarmData.toString());
            },
            error = { t->
                Log.e(TAG, t.toString());
            }
        )
    }

    fun updateAlarm(alarmVO: AlarmVO){
        repository.updateAlarm(alarmVO)
        getAlarm()
    }

    override fun onCleared() {
        useCaseAlarm.cancel()
        super.onCleared()

    }

}