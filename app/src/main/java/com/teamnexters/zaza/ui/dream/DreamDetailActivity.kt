package com.teamnexters.zaza.ui.dream

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.teamnexters.zaza.R
import kotlinx.android.synthetic.main.activity_dream_detail.*

class DreamDetailActivity : AppCompatActivity(), View.OnClickListener {


    // customized dialog 띄울 때 사용
    lateinit var custom_dialog: CustomDrDialog
    var itemPos: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dream_detail)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        custom_dialog = CustomDrDialog(this, this)

        val date = intent.extras.getString("date")
        val img = intent.extras.getString("img")
        itemPos = intent.extras.getInt("itemPos")

        setSupportActionBar(tb_dream_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        tb_title.text = date
        if (img != "") {
            val resourceId = this.resources.getIdentifier(img, "drawable", this.packageName)
            layout_dream_detail.setBackgroundResource(resourceId)
        }

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


}
