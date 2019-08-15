package com.teamnexters.zaza.util

import com.teamnexters.zaza.ui.alarm.data.vo.AlarmRealm
import com.teamnexters.zaza.ui.alarm.data.vo.AlarmVO
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults
import io.realm.kotlin.where
/**
 * Realm CRUD를 관리
 */
fun getAlarm(): RealmResults<AlarmRealm>?{
    val realm = Realm.getDefaultInstance()
    realm.beginTransaction()

    var alarm = realm.where(AlarmRealm::class.java)
        .equalTo("id", 0.toInt())
        .findAll()

    realm.commitTransaction()
    return alarm
}

fun updateAlarm(alarmVO: AlarmVO){
    val realm = Realm.getDefaultInstance()

    realm.beginTransaction()

    var alarm = realm.where<AlarmRealm>().contains("id","0").findFirst()!!
    alarm.isAfterFive = alarmVO.isAfterFive
    alarm.isVibrate = alarmVO.isVibrate
    alarm.sleepH = alarmVO.sleepH
    alarm.sleepM = alarmVO.sleepM
    alarm.wakeUpH = alarmVO.wakeUpH
    alarm.wakeUpM = alarmVO.wakeUpM

    realm.commitTransaction()
    realm.close()
}

fun insertAlarm(){
    val realm = Realm.getDefaultInstance()
    realm.beginTransaction()

    val weeks = RealmList<Boolean>()

    for(i in 0 ..6){
        weeks.add(false)
    }
    AlarmRealm(0,weeks,false, false,0,0,0,0).let { alarmRealm ->
        realm.insert(alarmRealm)

    }
    realm.commitTransaction()
    realm.close()

}