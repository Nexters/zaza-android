package com.teamnexters.zaza.ui.dream

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import com.teamnexters.zaza.R
import com.teamnexters.zaza.sample.firebase.models.Image
import com.teamnexters.zaza.sample.firebase.retrofit.ZazaService
import kotlinx.android.synthetic.main.activity_dream.*
import kotlinx.android.synthetic.main.activity_dream_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class DreamActivity : AppCompatActivity() {
    lateinit var dreamList: ArrayList<DreamItem>
    lateinit var sharedPref: SharedPreferences
    private lateinit var database: DatabaseReference
    private var appUuid = ""
    private val mcontext = this
    private var dreamAdapter:DreamItemAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dream)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        iv_dream_bottom_background.bringToFront()

        database = FirebaseDatabase.getInstance().reference
        sharedPref = getSharedPreferences("APP_INFO", Context.MODE_PRIVATE)
        appUuid = sharedPref.getString("UUID", null)
        Log.d("id",appUuid)
//        appUuid = "37bd17ec-6980-48f4-bb0a-bfef8b634437"

        dreamList = arrayListOf<DreamItem>()
        loadDreams(appUuid)

        layout_btn_dream_close.setOnClickListener {
            finish()
        }

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
                    if (itemPos != null) {
                        deleteDream(appUuid, dreamList[itemPos].id)
                        dreamList.removeAt(itemPos)
                        rv_dreams.adapter?.notifyItemRemoved(itemPos)
                        rv_dreams.adapter?.notifyItemRangeChanged((itemPos - 1), dreamList.size)
                    }
                    if(dreamList.isEmpty()){
                        tv_dream_empty.visibility = TextView.VISIBLE
                    }else{
                        tv_dream_empty.visibility = TextView.GONE
                    }
                }
                else -> {
                }
            }
        }
    }

    override fun onStart() {
        rv_dreams.adapter?.notifyDataSetChanged()
        super.onStart()
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

            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if(!dataSnapshot.hasChildren()){
                    tv_dream_empty.visibility = TextView.VISIBLE
                }else{
                    tv_dream_empty.visibility = TextView.GONE

                    for (dsp in dataSnapshot.children) {
                        var dId:String?
                        var bgImg:String?
                        var btImg:String?
                        var dateTime:Long?
                        var during:Double?

                        dsp.children.forEach { it ->
                            dId = it.key
                            bgImg = it.child("background_image").value.toString()
                            btImg = it.child("button_image").value.toString()
                            dateTime = it.child("datetime").value as Long
                            during = it.child("during").value as Double
                            if (dId != null) {
                                dreamList.add(DreamItem(dId!!, btImg, bgImg, dateTime, during))
                            }
                        }
                        dreamAdapter = DreamItemAdapter(mcontext, dreamList)
                        rv_dreams.adapter = dreamAdapter
                    }
                }

            }

        })


    }
}
