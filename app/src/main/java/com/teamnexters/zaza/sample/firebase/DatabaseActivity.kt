package com.teamnexters.zaza.sample.firebase

import android.os.Bundle
import com.google.firebase.database.*
import com.teamnexters.zaza.R
import com.teamnexters.zaza.base.BaseActivity
import com.teamnexters.zaza.databinding.ActivityDatabaseBinding
import com.teamnexters.zaza.sample.firebase.models.Dream
import kotlinx.android.synthetic.main.activity_database.*
import java.util.UUID
//push test
class DatabaseActivity : BaseActivity<ActivityDatabaseBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_database

    private lateinit var database: DatabaseReference
    private var uuid = UUID.randomUUID()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance().reference
        db_save_btn.setOnClickListener{
            saveDream(uuid.toString(), 0.2, "Hihi")
        }

        db_get_btn.setOnClickListener {
            getDreams(uuid.toString())
        }
    }

    private fun saveDream(uuid:String, during:Double, remark:String) {
        val datetime = System.currentTimeMillis()
        val user = Dream(datetime, during, remark)
        database.child("dream").child(uuid).child(UUID.randomUUID().toString()).setValue(user)
    }

    private fun getDreams(uuid:String){
        var sortByDatetiem = database.child("dream").child(uuid).orderByChild("datetime")
        //TODO: To be add a function that get all data from realtime database in firebase.
        sortByDatetiem.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                db_list_tv.setText("[Added]" + dataSnapshot.toString())
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
                db_list_tv.setText("[Changed]" + s)

            }
            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                db_list_tv.setText("[Removed]" + dataSnapshot.toString())

            }
            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {
                db_list_tv.setText("[Moved]" + s)
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

    }

}
