package com.teamnexters.zaza.ui.dream


import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.mikhaellopez.circularimageview.CircularImageView
import com.teamnexters.zaza.R
import java.text.SimpleDateFormat
import java.util.*


class DreamItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val date = itemView?.findViewById<TextView>(R.id.tv_date)
    val img = itemView?.findViewById<CircularImageView>(R.id.civ_dream)
    val sdf = SimpleDateFormat("MMMM.d", Locale.ENGLISH)

    fun bind (dreamitem: DreamItem, context: Context){

        if(dreamitem.button_img != ""){
            val resourceId = context.resources.getIdentifier(dreamitem.button_img, "drawable", context.packageName)
            img?.setImageResource(resourceId)
        }else{
        }

        date?.text = sdf.format(dreamitem.date)

    }


}