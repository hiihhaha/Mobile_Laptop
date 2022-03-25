package com.kotlin.mobile_laptop.ui.type_product

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

class TypesProductAdapter(
    var context: Context,
    var listTypeProduct : ArrayList<Product>,
    var setOnclickItem : (item : Product) -> Unit
)  : RecyclerView.Adapter<TypesProductAdapter.ViewHolder>() {



    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        var tv_Name = itemView.findViewById<TextView>(R.id.tv_item_name)
        var tv_Price = itemView.findViewById<TextView>(R.id.tv_price)
        var tv_Description = itemView.findViewById<TextView>(R.id.tv_mota)
        var img_Images = itemView.findViewById<ImageView>(R.id.img_type)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_typer_product,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var typeProduct = listTypeProduct[position]
        holder.tv_Name.text = typeProduct.name
        val formatter = DecimalFormat("#,###")
        val giaSP = formatter.format(typeProduct.price)
        holder.tv_Price.text = "Giá : $giaSP Đ"
        holder.tv_Description.text = typeProduct.description
        Glide.with(context).load(typeProduct.image).into(holder.img_Images)
        holder.itemView.setOnClickListener {
            setOnclickItem.invoke(typeProduct)
        }


    }

    override fun getItemCount(): Int = listTypeProduct.size


}