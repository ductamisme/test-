package com.example.learnagain.leftzoom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learnagain.R

class LeftAdapter(
    private var context: Context,
    private var dataset: List<Affirmation>
) : RecyclerView.Adapter<LeftAdapter.ItemLeftViewHolder>() {
    class ItemLeftViewHolder(private var view: View) : RecyclerView.ViewHolder(view) {
        val textview: TextView = view.findViewById(R.id.textView_listItem_items)
        val imageView: ImageView = view.findViewById(R.id.imageView_listItem_picture)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemLeftViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.item_left, parent, false)
        return ItemLeftViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemLeftViewHolder, position: Int) {
        val item = dataset[position]
        holder.imageView.setImageResource(item.ImageResourceId)
        holder.textview.text = context.resources.getString(item.StringResourceId)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}