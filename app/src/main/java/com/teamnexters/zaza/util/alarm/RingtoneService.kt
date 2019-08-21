package com.teamnexters.zaza.util.alarm

import android.app.Service
import android.content.Intent
import android.media.AsyncPlayer
import android.media.MediaPlayer
import android.os.IBinder
import com.teamnexters.zaza.R

class RingtoneService : Service() {

    lateinit var mediaPlayer: MediaPlayer
    var startId: Int = 0
    var isRunning: Boolean = false

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val getState = intent!!.extras.getString("state")
        var s = startId

        when(getState){
            "alarmOn" -> s = 1
            "alarmOff" -> s = 0
        }

        if(!this.isRunning && s == 1){
            mediaPlayer = MediaPlayer.create(this, R.raw.germ_warfare)
            mediaPlayer.start()

            this.isRunning = true
            this.startId = 0
        } else if(this.isRunning && s == 0){
            mediaPlayer.stop()
            mediaPlayer.reset()
            mediaPlayer.release()

            this.isRunning = false
            this.startId = 0
        } else if(!this.isRunning && startId == 0) {

            this.isRunning = false
            this.startId = 0

        } else if(this.isRunning && startId == 1){
            this.isRunning = true
            this.startId = 1
        }

        return START_NOT_STICKY;
    }
}