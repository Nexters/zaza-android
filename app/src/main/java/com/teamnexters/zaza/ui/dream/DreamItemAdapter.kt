package com.teamnexters.zaza.ui.dream

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamnexters.zaza.R

class DreamItemAdapter : RecyclerView.Adapter<DreamItemViewHolder>{
    private var items: ArrayList<DreamItem>
    private val context: Context

    constructor(context: Context, items: ArrayList<DreamItem>) : super() {
        this.items = items
        this.context = context
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
    }
}