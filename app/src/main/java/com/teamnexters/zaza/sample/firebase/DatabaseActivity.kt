package com.teamnexters.zaza.sample.firebase

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import com.teamnexters.zaza.R
import com.teamnexters.zaza.base.BaseActivity
import com.teamnexters.zaza.databinding.ActivityDatabaseBinding
import com.teamnexters.zaza.sample.firebase.models.Dream
import kotlinx.android.synthetic.main.activity_database.*
import java.util.UUID
import com.google.firebase.database.DataSnapshot


class DatabaseActivity : BaseActivity<ActivityDatabaseBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_database

    private lateinit var database: DatabaseReference
    private val sharedPref: SharedPreferences = getSharedPreferences("APP_INFO", Context.MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance().reference
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
            val sortByDatetiem = database.child("dream").orderByKey().equalTo(appUuid)

            // 전체 데이터를 가져옴
            sortByDatetiem.addListenerForSingleValueEvent(object : ValueEventListener {
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
