package com.teamnexters.zaza

import android.app.Application
import android.app.NotificationChannel
import android.content.Context
import android.os.Build
import com.teamnexters.zaza.util.NotificationManager
import io.realm.Realm
import io.realm.RealmConfiguration

class ZazaApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        val config = RealmConfiguration.Builder().name("zaza.realm").build()
        Realm.setDefaultConfiguration(config)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager.createChannel(this)
        }
    }


}