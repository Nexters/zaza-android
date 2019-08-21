package com.nexters.zaza.ui.dream

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.*
import com.nexters.zaza.R
import kotlinx.android.synthetic.main.activity_dream.*

class DreamActivity : AppCompatActivity() {
    lateinit var dreamList: ArrayList<DreamItem>
    lateinit var sharedPref: SharedPreferences
    private lateinit var database: DatabaseReference
    private var appUuid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dream)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        database = FirebaseDatabase.getInstance().reference
        sharedPref = getSharedPreferences("APP_INFO", Context.MODE_PRIVATE)
        appUuid = sharedPref.getString("UUID", null)
//        appUuid = "a1c0532d-d11f-4f7c-a7fb-69fcc56f4cd0"

        dreamList = arrayListOf<DreamItem>()

        layout_btn_dream_close.setOnClickListener {
            finish()
        }


        loadDreams(appUuid)

        val dreamAdapter = DreamItemAdapter(this, dreamList)
        rv_dreams.adapter = dreamAdapter

        val gm = GridLayoutManager(this, 3)

        rv_dreams.layoutManager = gm
        rv_dreams.setHasFixedSize(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
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

        if (requestCode == 3000) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    if (itemPos != null && dreamList[itemPos] != null) {
                        deleteDream(appUuid, dreamList[itemPos].id)
                        dreamList.removeAt(itemPos)
                        rv_dreams.adapter?.notifyItemRemoved(itemPos)
                        rv_dreams.adapter?.notifyItemRangeChanged((itemPos - 1), dreamList.size)
                    }
                }
                else -> {
                }
            }
        }
    }

    private fun deleteDream(appUuid: String, dreamId:String) {
        database.child("dream").child(appUuid).child(dreamId).removeValue()
//        Log.d("")
    }

    private fun loadDreams(appUuid: String) {

        val sortByDatetime = database.child("dream").orderByKey().equalTo(appUuid)

        // 전체 데이터를 가져옴
        sortByDatetime.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dsp in dataSnapshot.getChildren()) {

                    dsp.children.forEach { it ->
                        val dId = it.key
                        val bgImg = it.child("background_image").value.toString()
                        val btImg = it.child("button_image").value.toString()
                        val dateTime = it.child("datetime").value as Long
                        val during = it.child("during").value as Double
                        if (dId != null) {
                            dreamList.add(DreamItem(dId, btImg, bgImg, dateTime, during))
                        }
                    }
                    rv_dreams.adapter?.notifyDataSetChanged()

                }

//                    Log.d("test", myList.toString())
            }
        })


//        dreamList.add(DreamItem("https://storage.googleapis.com/zaza-249404.appspot.com/png_box/gradation_type-07-1-1.png","https://storage.googleapis.com/zaza-249404.appspot.com/background_img/gradation_type-07.png",1566023556253,0.2))
//        dreamList.add(DreamItem("https://storage.googleapis.com/zaza-249404.appspot.com/png_box/gradation_type-07-1-2.png","https://storage.googleapis.com/zaza-249404.appspot.com/background_img/gradation_type-07.png",1566023556253,0.2))
//        dreamList.add(DreamItem("https://storage.googleapis.com/zaza-249404.appspot.com/png_box/gradation_type-07-1-3.png","https://storage.googleapis.com/zaza-249404.appspot.com/background_img/gradation_type-07.png",1566023556253,0.2))
//        dreamList.add(DreamItem("https://storage.googleapis.com/zaza-249404.appspot.com/png_box/gradation_type-07-4-2.png","https://storage.googleapis.com/zaza-249404.appspot.com/background_img/gradation_type-07.png",1566023556253,0.2))
//        dreamList.add(DreamItem("https://storage.googleapis.com/zaza-249404.appspot.com/png_box/gradation_type-07-4-3.png","https://storage.googleapis.com/zaza-249404.appspot.com/background_img/gradation_type-07.png",1566023556253,0.2))
//        dreamList.add(DreamItem("https://storage.googleapis.com/zaza-249404.appspot.com/png_box/gradation_type-07-5-1.png","https://storage.googleapis.com/zaza-249404.appspot.com/background_img/gradation_type-07.png",1566023556253,0.2))
//        dreamList.add(DreamItem("loading","bg_white",1566023555701,0.2))
//        dreamList.add(DreamItem("loading","bg_white",1566023555376,0.2))
//        dreamList.add(DreamItem("loading","bg_white",1566023556410,0.2))
//        dreamList.add(DreamItem("loading","bg_white",1566023556095,0.2))

    }
}
