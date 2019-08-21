package com.nexters.zaza.sample.firebase

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import com.nexters.zaza.R
import com.nexters.zaza.base.BaseActivity
import com.nexters.zaza.databinding.ActivityDatabaseBinding
import com.nexters.zaza.sample.firebase.models.Dream
import kotlinx.android.synthetic.main.activity_database.*
import java.util.UUID
import com.google.firebase.database.DataSnapshot

//TODO: 꿈이 저장 될 때 아래 Save Dream 과 같이 꿈을 저장해야함, 알아 둬야 할 게 저장 시 **Image Activity**의 Retrofit API를 호출하여,
//TODO: 이미지 주소도 같이 저장해야함. 이미지는 백그라운드 이미지와 그라디언트 아이콘 이미지 두개 임. (JK님)
class DatabaseActivity : BaseActivity<ActivityDatabaseBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_database

    private lateinit var database: DatabaseReference
    private val sharedPref: SharedPreferences = getSharedPreferences("APP_INFO", Context.MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        database = FirebaseDatabase.getInstance().reference
        database.keepSynced(true)
        db_save_btn.setOnClickListener {
            saveDream(0.2, "Hihi")
        }

        db_get_btn.setOnClickListener {
            getDreams()
        }
    }

    private fun saveDream(during: Double, remark: String) {
        val datetime = System.currentTimeMillis()
        val user = Dream(datetime, during, remark)
        val appUuid = sharedPref.getString("UUID", null)
        if (appUuid != null)
            database.child("dream").child(appUuid).child(UUID.randomUUID().toString()).setValue(user)
        else
            Log.d("Database", "APP UUID IS NULL")
    }

    private fun getDreams() {
        val appUuid = sharedPref.getString("UUID", null)
        if (appUuid != null) {
            val sortByDatetime = database.child("dream").orderByKey().equalTo(appUuid)

            // 전체 데이터를 가져옴
            sortByDatetime.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var DreamList = ArrayList<String>()

                    for (dsp in dataSnapshot.getChildren()) {

                        DreamList.add(dsp.toString())
                    }

                    Log.d("test", DreamList.toString())
                }
            })
        } else {
            Log.d("Database", "APP UUID IS NULL")
        }


    }

}
