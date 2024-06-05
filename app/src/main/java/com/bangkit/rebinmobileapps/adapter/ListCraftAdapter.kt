package com.bangkit.rebinmobileapps.adapter

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ListCraftAdapter(private val list: List<String>) : RecyclerView.Adapter<ListCraftAdapter.MyView>() {
    inner class MyView
        (view: View) : RecyclerView.ViewHolder(view) {
        // Text View


        // initialise TextView with id
        var textView: TextView = view
            .findViewById<View>(R.id.list) as TextView
    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyView {
        // Inflate item.xml using LayoutInflator

        val itemView
                : View = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.list_content,
                parent,
                false
            )


        // return itemView
        return MyView(itemView)
    }

    // Override onBindViewHolder which deals
    // with the setting of different data
    // and methods related to clicks on
    // particular items of the RecyclerView.
    override fun onBindViewHolder(
        holder: MyView,
        position: Int
    ) {
        // Set the text of each item of
        // Recycler view with the list items

        holder.textView.text = list[position]
    }

    // Override getItemCount which Returns
    // the length of the RecyclerView.
    override fun getItemCount(): Int {
        return list.size
    }
}