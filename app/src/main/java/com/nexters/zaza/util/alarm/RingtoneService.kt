package com.nexters.zaza.util.alarm

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.nexters.zaza.R

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
        mediaPlayer = MediaPlayer.create(this, R.raw.germ_warfare)
        mediaPlayer.start()

        return START_NOT_STICKY;
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.reset()
        mediaPlayer.release()
    }
}