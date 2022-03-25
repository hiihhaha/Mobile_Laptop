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
import com.kotlin.mobile_laptop.model.Product
import java.text.DecimalFormat

class ProductAdapter(
    var context: Context,
    var listProduct : ArrayList<Product>,
    var onCLickItem : (item : Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder (itemVew : View) : RecyclerView.ViewHolder(itemVew){
        var imgProduct = itemVew.findViewById<ImageView>(R.id.img_itemimage)
        var tv_Name = itemVew.findViewById<TextView>(R.id.tv_itemname)
        var tv_Price = itemVew.findViewById<TextView>(R.id.tv_itemprice)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_product,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var product = listProduct[position]
        holder.tv_Name.text = product.name
        val formatter = DecimalFormat("#,###")
        val giaSP = formatter.format(product.price)
        holder.tv_Price.text = "Giá : $giaSP Đ"
        Glide.with(context).load(product.image).into(holder.imgProduct)
        holder.itemView.setOnClickListener {
            onCLickItem.invoke(product)
        }

    }

    override fun getItemCount(): Int = listProduct.size


}