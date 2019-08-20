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
    lateinit var dreamList:ArrayList<DreamItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dream)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        layout_btn_dream_close.setOnClickListener{
            finish()
        }

        dreamList = arrayListOf<DreamItem>()
        loadDreams()

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
                    if(itemPos != null && dreamList[itemPos] != null) {
                        dreamList.removeAt(itemPos)
                        rv_dreams.adapter?.notifyItemRemoved(itemPos)
                        rv_dreams.adapter?.notifyItemRangeChanged((itemPos-1), dreamList.size)
                    }
                }
                else -> {
                }
            }
        }
    }

    private fun loadDreams(){
        dreamList.add(DreamItem("loading","bg_white",1566023556253,0.2))
        dreamList.add(DreamItem("loading","bg_white",1566023555701,0.2))
        dreamList.add(DreamItem("loading","bg_white",1566023555376,0.2))
        dreamList.add(DreamItem("loading","bg_white",1566023556410,0.2))
        dreamList.add(DreamItem("loading","bg_white",1566023556095,0.2))

    }
}
