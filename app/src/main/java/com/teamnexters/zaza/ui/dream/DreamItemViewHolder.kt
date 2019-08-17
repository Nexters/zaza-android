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


class DreamItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val date = itemView?.findViewById<TextView>(R.id.tv_date)
    val img = itemView?.findViewById<CircularImageView>(R.id.civ_dream)

    fun bind (dreamitem: DreamItem, context: Context){

        if(dreamitem.photo != ""){
            val resourceId = context.resources.getIdentifier(dreamitem.photo, "drawable", context.packageName)
            img?.setImageResource(resourceId)
        }else{
            img?.setImageResource(R.drawable.loading)
        }

        date?.text = dreamitem.date

    }


}