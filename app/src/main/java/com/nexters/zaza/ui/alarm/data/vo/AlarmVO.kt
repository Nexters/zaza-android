package com.nexters.zaza.ui.alarm.data.vo

data class AlarmVO(
    var weeks: ArrayList<Boolean>,
    var isVibrate: Boolean,
    var isAfterFive: Boolean,
    var wakeUpH: Int,
    var wakeUpM: Int,
    var sleepH: Int,
    var sleepM: Int
)