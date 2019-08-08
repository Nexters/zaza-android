package com.teamnexters.zaza.ui.dream


import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.Image
import android.view.View
import android.widget.TextView
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
            img?.setImageResource(R.drawable.ic_launcher_foreground)
        }

        date?.text = dreamitem.date
    }
}