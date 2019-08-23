package com.nexters.zaza.ui.dream


import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import com.nexters.zaza.R

import kotlinx.android.synthetic.main.activity_dream_detail.*

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class DreamItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    val date = itemView?.findViewById<TextView>(R.id.tv_date)
    val civ = itemView?.findViewById<CircularImageView>(R.id.civ_dream)
    val sdf = SimpleDateFormat("MMMM.d", Locale.ENGLISH)

    fun bind(dreamitem: DreamItem, context: Context) {
        //        if(dreamitem.button_img != ""){
        //            val resourceId = context.resources.getIdentifier(dreamitem.button_img, "drawable", context.packageName)
        //            civ?.setImageResource(resourceId)
        //        }else{
        //        }

        Picasso.get().load(dreamitem.button_img).resize(92,92).into(object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                Log.d("image", "Prepare Load")
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Log.d("image", "Failed")
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                Log.d("image", "Imaged Loaded")
                civ.setImageDrawable(BitmapDrawable(context.resources, bitmap))
            }
        })

        date?.text = sdf.format(dreamitem.date)
    }

}