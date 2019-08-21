package com.nexters.zaza.ui.dream

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nexters.zaza.R
import kotlin.collections.ArrayList
import com.nexters.zaza.ui.dream.DreamItem as DreamItem

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
        holder?.itemView.setOnClickListener {

            detailIntent.putExtra("date", items[position].date)
            detailIntent.putExtra("during", items[position].during)
            detailIntent.putExtra("backgroundImg", items[position].background_img)
            detailIntent.putExtra("itemPos", position)

            if(!DreamDetailActivity.ACTIVE)
                (context as DreamActivity).startActivityForResult(detailIntent, 3000)
        }
    }


}