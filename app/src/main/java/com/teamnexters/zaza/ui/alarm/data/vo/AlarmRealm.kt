package com.teamnexters.zaza.ui.alarm.data.vo

import io.realm.RealmList
import io.realm.RealmObject

open class AlarmRealm(
    var id: Int,
    var weeks: RealmList<Boolean>,
    var isVibrate: Boolean,
    var isAfterFive: Boolean,
    var wakeUpH: Int,
    var wakeUpM: Int,
    var sleepH: Int,
    var sleepM: Int
) : RealmObject() {
    constructor(): this(
        id = 0,
        weeks = RealmList(),
        isAfterFive = false,
        isVibrate = false,
        wakeUpH = 0,
        wakeUpM = 0,
        sleepH = 0,
        sleepM = 0
    )
}