package com.teamnexters.zaza

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class ZazaApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        val config = RealmConfiguration.Builder().name("zaza.realm").build()
        Realm.setDefaultConfiguration(config)

    }


}