package com.teamnexters.zaza.ui.alarm.data.vo

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import io.realm.RealmList

data class AlarmVO(
    var weeks: ArrayList<Boolean>,
    var isVibrate: Boolean,
    var isAfterFive: Boolean,
    var wakeUpH: Int,
    var wakeUpM: Int,
    var sleepH: Int,
    var sleepM: Int
)