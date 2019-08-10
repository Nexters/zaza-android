package com.teamnexters.zaza.ui.alarm.vo

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class AlarmVO(
    weeks: Array<Boolean>,
    isVibrate: Boolean,
    isAfterFive: Boolean
) : RealmObject() {
    constructor(): this(
        weeks = Array(7, {i -> false}),
        isAfterFive = false,
        isVibrate = false
    )
}