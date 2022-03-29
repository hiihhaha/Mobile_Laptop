package com.kotlin.mobile_laptop.ui.cart

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kotlin.mobile_laptop.R
import com.kotlin.mobile_laptop.model.CartControler
import com.kotlin.mobile_laptop.model.OrderProduct
import java.text.DecimalFormat

class CartAdapter(
    var context: Context,
    var listOder: ArrayList<OrderProduct>,
    var onItemOderChange: (OrderProduct) -> Unit
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var order = listOder[position]
        var product = order.product
        Glide.with(context).load(product.image).into(holder.img_Picture)
        holder.tv_name.text = product.name
        val formatter = DecimalFormat("#,###")
        val giaSP = formatter.format(product.price)
        holder.tv_price.text = "Giá : $giaSP Đ"
        holder.tv_Amount.text = order.amount.toString()
        holder.tv_Total_Money.text = (product.price * order.amount).toString()
        holder.btn_Minus.setOnClickListener {
            if (order.amount > 1) {
                order.amount -= 1
                holder.tv_Amount.text = order.amount.toString()
                holder.tv_Total_Money.text = (product.price * order.amount).toString()
                onItemOderChange.invoke(order)
            } else {
                AlertDialog.Builder(context)
                    .setTitle("Thông Báo")
                    .setMessage("Bạn có muốn xóa sản phẩm này khỏi giỏ hàng")
                    .setPositiveButton(
                        "Đồng ý"

                    ) { _, _ ->
                        CartControler.arrayCart.remove(order)
                        notifyItemChanged(position)
                        onItemOderChange.invoke(order)
                    }.setNegativeButton("Hủy bỏ") { dialog, _ ->
                        dialog.dismiss()
                    }.show()
            }
        }

        holder.btn_Plus.setOnClickListener {
            order.amount += 1
            holder.tv_Amount.text = order.amount.toString()
            holder.tv_Total_Money.text = (product.price * order.amount).toString()
            onItemOderChange.invoke(order)
        }
    }
    override fun getItemCount(): Int = listOder.size

    class ViewHolder(itemVew: View) : RecyclerView.ViewHolder(itemVew) {
        var btn_Minus = itemVew.findViewById<ImageView>(R.id.img_minus)
        var btn_Plus = itemVew.findViewById<ImageView>(R.id.img_plus)
        var img_Picture = itemVew.findViewById<ImageView>(R.id.img_Cart)
        var tv_name = itemVew.findViewById<TextView>(R.id.tv_name)
        var tv_price = itemVew.findViewById<TextView>(R.id.tv_price)
        var tv_Amount = itemVew.findViewById<TextView>(R.id.tv_Amount)
        var tv_Total_Money = itemVew.findViewById<TextView>(R.id.tv_total_money)
    }
}