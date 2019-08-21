package com.teamnexters.zaza.ui.dream

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import com.teamnexters.zaza.R
import kotlinx.android.synthetic.main.activity_dream_detail.*
import kotlinx.android.synthetic.main.activity_image.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class DreamDetailActivity : AppCompatActivity(), View.OnClickListener {


    // customized dialog 띄울 때 사용
    lateinit var custom_dialog: CustomDrDialog
    var itemPos: Int = -1
    companion object {
        var ACTIVE = false      //실행 여부 체크
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dream_detail)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        val imageLoadTarget: Target = object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                Log.d("image", "Prepare Load")
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Log.d("image", "Failed")
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                Log.d("image", "Imaged Loaded")
                layout_dream_detail.background = BitmapDrawable(resources, bitmap)
            }

        }

        custom_dialog = CustomDrDialog(this, this)


        val date = intent.extras.getLong("date")
        val during = intent.extras.getDouble("during")
        val backgroundImg = intent.extras.getString("backgroundImg")
        itemPos = intent.extras.getInt("itemPos")

        var sdf = SimpleDateFormat("MMMM.d", Locale.ENGLISH)
        val title = sdf.format(date)
        sdf = SimpleDateFormat("hh:mm")
        val sleepTime = sdf.format(date)
        val wakeTime = sdf.format((date + during * 3600000))

        setSupportActionBar(tb_dream_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        tb_title.text = title
        tv_sleepTime.text = sleepTime
        tv_wakeTime.text = wakeTime
        tv_duringTime.text = "+$during"

//        if (backgroundImg != "") {
//            val resourceId = this.resources.getIdentifier(backgroundImg, "drawable", this.packageName)
//            layout_dream_detail.setBackgroundResource(resourceId)
//        }
        Picasso.get().load(backgroundImg).placeholder(R.drawable.loading).into(imageLoadTarget)
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
        super.onStart()
        ACTIVE = true
    }

    override fun onStop() {
        super.onStop()
        ACTIVE = false
    }
}
