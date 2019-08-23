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

        date?.text = sdf.format(dreamitem.date)
    }

}