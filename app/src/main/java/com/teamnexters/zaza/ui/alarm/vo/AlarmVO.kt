package com.teamnexters.zaza.ui.alarm.vo

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class AlarmVO(
    var id: Int,
    var weeks: Array<Boolean>,
    var isVibrate: Boolean,
    var isAfterFive: Boolean,
    var wakeUpH: Int,
    var wakeUpM: Int,
    var sleepH: Int,
    var sleepM: Int
) : RealmObject() {
    constructor(): this(
        id = 0,
        weeks = Array(7, {i -> false}),
        isAfterFive = false,
        isVibrate = false,
        wakeUpH = 0,
        wakeUpM = 0,
        sleepH = 0,
        sleepM = 0
    )
}