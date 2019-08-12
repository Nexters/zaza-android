package com.teamnexters.zaza.ui.dream

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.teamnexters.zaza.R
import kotlinx.android.synthetic.main.activity_dream.*

class DreamActivity : AppCompatActivity() {

    var dreamList = arrayListOf<DreamItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dream)

        dreamList.add(DreamItem("", "August 11"))
        dreamList.add(DreamItem("", "August 12"))
        dreamList.add(DreamItem("", "August 13"))
        dreamList.add(DreamItem("", "August 14"))

        val dreamAdapter = DreamItemAdapter(this, dreamList)
        rv_dreams.adapter = dreamAdapter

        val gm = GridLayoutManager(this,3)
        

        rv_dreams.layoutManager = gm
        rv_dreams.setHasFixedSize(true)
    }
}
