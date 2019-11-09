package com.nexters.zaza.ui.dream

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.nexters.zaza.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_dream_detail.*
import java.text.SimpleDateFormat
import java.util.*

class DreamDetailActivity : AppCompatActivity(), View.OnClickListener {


    // customized dialog 띄울 때 사용
    lateinit var custom_dialog: CustomDreamDialog
    var itemPos: Int = -1

    companion object {
        var ACTIVE = false      //실행 여부 체크
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dream_detail)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
//        iv_dream_detail_bottom_background.bringToFront()
        layout_dream_detail.visibility = View.INVISIBLE
        layout_dream_detail.tag = object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                Log.d("image", "Prepare Load")
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Log.d("image", "Failed")
                layout_dream_detail.setBackgroundResource(R.drawable.bg_white)
                layout_dream_detail.visibility = View.VISIBLE
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                Log.d("image", "Imaged Loaded")
                layout_dream_detail.background = BitmapDrawable(resources, bitmap)
                layout_dream_detail.visibility = View.VISIBLE
            }
        }

        custom_dialog = CustomDreamDialog(this, this)


        val date = intent.extras.getLong("date")
        val during = intent.extras.getDouble("during")

        itemPos = intent.extras.getInt("itemPos")

        var sdf = SimpleDateFormat("MMMM.d", Locale.ENGLISH)
        val title = sdf.format(date)
        sdf = SimpleDateFormat("hh:mm")
        val sdf1 = SimpleDateFormat("HH:mm")
        sdf1.timeZone = TimeZone.getTimeZone("UTC")
        val sleepTime = sdf.format(date)
        val wakeTime = sdf.format((date + during * 3600000))
        val duringTime = during * 3600000


        setSupportActionBar(tb_dream_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        tb_title.text = title
        tv_sleepTime.text = sleepTime
        tv_wakeTime.text = wakeTime
        tv_duringTime.text = sdf1.format(duringTime)

        ACTIVE = true
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dream_detail_bar_action, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.action_delete -> {
                custom_dialog.show()
            }
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onClick(v: View?) {
        custom_dialog.dismiss()

        val resultIntent = Intent()
        resultIntent.putExtra("itemPos", itemPos)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    override fun onStart() {
        val backgroundImg = intent.extras.getString("backgroundImg")
        Picasso.get().load(backgroundImg).into(layout_dream_detail.tag as Target)

        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        ACTIVE = false
    }

    override fun onDestroy() {
//        ACTIVE = false
        super.onDestroy()
    }
}
