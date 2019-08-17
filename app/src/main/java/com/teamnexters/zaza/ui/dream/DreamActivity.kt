package com.teamnexters.zaza.ui.dream

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.teamnexters.zaza.R
import kotlinx.android.synthetic.main.activity_dream.*

class DreamActivity : AppCompatActivity() {

    var dreamList = arrayListOf<DreamItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dream)

        setSupportActionBar(tb_dream_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        dreamList.add(DreamItem("loading", "August 11"))
        dreamList.add(DreamItem("loading", "August 12"))
        dreamList.add(DreamItem("loading", "August 13"))
        dreamList.add(DreamItem("loading", "August 14"))

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
}
