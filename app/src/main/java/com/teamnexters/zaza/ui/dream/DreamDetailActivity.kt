package com.teamnexters.zaza.ui.dream

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.teamnexters.zaza.R
import kotlinx.android.synthetic.main.activity_dream_detail.*

class DreamDetailActivity : AppCompatActivity() {

    // customized dialog 띄울 때 사용
    lateinit var custom_dialog:CustomDrDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dream_detail)

        setSupportActionBar(tb_dream_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dream_detail_bar_action, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.action_delete -> {
               clickDelete()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun clickDelete(){
        custom_dialog = CustomDrDialog(this)

        custom_dialog.show()

    }
}
