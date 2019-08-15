package com.teamnexters.zaza.ui.alarm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.teamnexters.zaza.base.BaseViewModel
import com.teamnexters.zaza.ui.alarm.repository.AlarmRepositoryImpl
import com.teamnexters.zaza.ui.alarm.data.vo.AlarmRealm
import com.teamnexters.zaza.ui.alarm.data.vo.AlarmVO
import com.teamnexters.zaza.ui.alarm.usecase.GetAlarm
import io.reactivex.schedulers.Schedulers

class AlarmViewModel: BaseViewModel() {

    val TAG = "AlarmViewMoel"
    val mutableAlarmData = MutableLiveData<AlarmVO>()
    private val repository =  AlarmRepositoryImpl()

    init {
        Log.e(TAG, "ViewMoel init")
        GetAlarm(repository, Schedulers.io())(
            success = { alarm ->
                mutableAlarmData.value = alarm
                Log.e(TAG, mutableAlarmData.toString());
            },
            error = { t->
                Log.e(TAG, t.toString());
            }
        )
    }



}