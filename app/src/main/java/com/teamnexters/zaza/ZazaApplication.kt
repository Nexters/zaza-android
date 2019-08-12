package com.teamnexters.zaza

import android.app.Application
import io.realm.Realm

class ZazaApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

    }


}