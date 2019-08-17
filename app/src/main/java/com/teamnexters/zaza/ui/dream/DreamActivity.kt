package com.teamnexters.zaza.ui.dream

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.teamnexters.zaza.R
import kotlinx.android.synthetic.main.activity_dream.*

class DreamActivity : AppCompatActivity() {
    private val Delete_Requst = 3000
    var dreamList = arrayListOf<DreamItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dream)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setSupportActionBar(tb_dream_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        dreamList.add(DreamItem("loading", "August 11"))
        dreamList.add(DreamItem("loading", "August 12"))
        dreamList.add(DreamItem("loading", "August 13"))
        dreamList.add(DreamItem("loading", "August 14"))
        dreamList.add(DreamItem("loading", "August 15"))
        dreamList.add(DreamItem("loading", "August 16"))
        dreamList.add(DreamItem("loading", "August 17"))
        dreamList.add(DreamItem("loading", "August 18"))
        dreamList.add(DreamItem("loading", "August 19"))
        dreamList.add(DreamItem("loading", "August 20"))
        dreamList.add(DreamItem("loading", "August 21"))
        dreamList.add(DreamItem("loading", "August 22"))

        val dreamAdapter = DreamItemAdapter(this, dreamList)
        rv_dreams.adapter = dreamAdapter

        val gm = GridLayoutManager(this,3)

        rv_dreams.layoutManager = gm
        rv_dreams.setHasFixedSize(true)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val itemPos = data?.getIntExtra("itemPos", -1)

        if(requestCode == 3000){
            when(resultCode){
                Activity.RESULT_OK -> {
                    if(itemPos != null) {
                        dreamList.removeAt(itemPos)
                        rv_dreams.adapter?.notifyItemRemoved(itemPos)
                        rv_dreams.adapter?.notifyItemRangeChanged((itemPos-1), dreamList.size)
                    }
                }
                else -> {
                    Toast.makeText(this,"$resultCode", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}
