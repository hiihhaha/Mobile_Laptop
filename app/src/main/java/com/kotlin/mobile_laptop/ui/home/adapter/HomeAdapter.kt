package com.kotlin.mobile_laptop.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kotlin.mobile_laptop.R
import com.kotlin.mobile_laptop.model.ItemMenu
import com.kotlin.mobile_laptop.model.Product

class HomeAdapter(
    var context: Context,
    var listItemMenu: ArrayList<ItemMenu>,
    var OnclickItemMenu : (item : ItemMenu) ->Unit
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_image = itemView.findViewById<ImageView>(R.id.img_item)
        var tv_name = itemView.findViewById<TextView>(R.id.tv_item_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_menu,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemMenu = listItemMenu.getOrNull(position)
        itemMenu?.image?.let { image ->
            Glide.with(context).load(image).into(holder.tv_image)
        }

        holder.tv_name.text = itemMenu?.name
        holder.itemView.setOnClickListener {
            if (itemMenu != null) {
                OnclickItemMenu.invoke(itemMenu)
            }
        }
    }

    override fun getItemCount(): Int = listItemMenu.size
}