package com.nexters.zaza

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import io.realm.Realm
import io.realm.RealmConfiguration

class ZazaApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        val config = RealmConfiguration.Builder().name("zaza.realm").build()
        Realm.setDefaultConfiguration(config)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //string xml로 저장해야함
            val channelId = "1"
            val channelName = "alarm"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }

    }


}