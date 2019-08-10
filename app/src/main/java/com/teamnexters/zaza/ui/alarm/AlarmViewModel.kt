package com.teamnexters.zaza.ui.alarm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamnexters.zaza.base.BaseViewModel
import com.teamnexters.zaza.sample.DisposableViewModel
import com.teamnexters.zaza.sample.Sample
import com.teamnexters.zaza.sample.SampleViewModel
import com.teamnexters.zaza.ui.alarm.repository.AlarmRepositoryImpl

class AlarmViewModel: BaseViewModel() {

//    var weeks : MutableLiveData<List<Boolean>>
    private val repository =  AlarmRepositoryImpl()
    init {


    }

    override fun onCleared() {
        super.onCleared()
    }
}