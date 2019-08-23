package com.teamnexters.zaza.ui.dream

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.teamnexters.zaza.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.teamnexters.zaza.ui.dream.DreamItem as DreamItem
import android.view.animation.OvershootInterpolator
import androidx.core.view.ViewCompat.animate
import android.R.attr.scaleX
import android.R.attr.scaleY
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.view.ViewCompat.setScaleY
import androidx.core.view.ViewCompat.setScaleX
import androidx.core.view.ViewCompat.setAlpha
import android.text.TextUtils
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception


class DreamItemAdapter : RecyclerView.Adapter<DreamItemViewHolder> {
    private var items: ArrayList<DreamItem>
    private val context: Context
    private val detailIntent: Intent

    constructor(context: Context, items: ArrayList<DreamItem>) : super() {
        this.items = items
        this.context = context
        detailIntent = Intent(context, DreamDetailActivity::class.java)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DreamItemViewHolder {
        val sampleLayout: View = LayoutInflater.from(context).inflate(R.layout.item_dream, parent, false)
        return DreamItemViewHolder(sampleLayout)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: DreamItemViewHolder, position: Int) {
        holder?.bind(items[position], context)
        holder?.civ.tag = object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                Log.d("image", "Prepare Load")
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Log.d("image", "Failed")
    //                civ?.setImageResource(R.mipmap.ic_launcher)
                holder.civ.setImageResource(R.mipmap.ic_launcher)
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                Log.d("image", "Imaged Loaded")
                holder.civ.setImageDrawable(BitmapDrawable(context.resources, bitmap))
                holder.civ.borderWidth = 0.0f
            }
        }

        Picasso.get().load(items[position].button_img).resize(92, 92).into(holder.civ.tag as Target)

        holder?.itemView.setOnClickListener {
            detailIntent.putExtra("date", items[position].date)
            detailIntent.putExtra("during", items[position].during)
            detailIntent.putExtra("backgroundImg", items[position].background_img)
            detailIntent.putExtra("itemPos", position)

            if(!DreamDetailActivity.ACTIVE)
                (context as DreamActivity).startActivityForResult(detailIntent, 3000)
        }

    }

//    override fun onBindViewHolder(holder: DreamItemViewHolder, position: Int, payloads: MutableList<Any>) {
//        if(payloads.isEmpty()) {
//            super.onBindViewHolder(holder, position, payloads)
//        }else{
//            for (payload in payloads) {
//                if (payload is String) {
//                    if (TextUtils.equals(payload, "update") && holder is DreamItemViewHolder) {
//                        notifyItemChanged(position)
//                    }
//                }
//            }
//        }
//    }
}