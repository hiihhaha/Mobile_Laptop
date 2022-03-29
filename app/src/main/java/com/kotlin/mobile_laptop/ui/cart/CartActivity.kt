package com.kotlin.mobile_laptop.ui.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.mobile_laptop.R
import com.kotlin.mobile_laptop.model.CartControler
import com.kotlin.mobile_laptop.model.OrderProduct
import com.kotlin.mobile_laptop.ui.pay.PayActivity
import kotlinx.android.synthetic.main.activity_cart.*
import java.text.DecimalFormat

class CartActivity : AppCompatActivity() {
    var adapterCart : CartAdapter? = null
    var listCart = CartControler.arrayCart
    var totalMoney = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        initControl ()
        setupRecyclerviewGioHang()
        updateTotalMoney()
    }

    private fun setupRecyclerviewGioHang() {
        adapterCart = CartAdapter(this,listCart,::onItemOrderChange)
        rcv_Cart.adapter = adapterCart
        rcv_Cart.layoutManager = LinearLayoutManager(this)
    }

    private fun onItemOrderChange (orderProduct: OrderProduct) {
        updateTotalMoney()
    }

    private fun initControl (){
        img_back2.setOnClickListener{onBackPressed()}
        btn_OderPay.setOnClickListener {
            var intent = Intent(this, PayActivity::class.java)
            intent.putExtra("totalMoney",totalMoney)
            startActivity(intent)
        }
    }


    //có sự thay đổi từ tongtiensp gọi lên biến toàn cục
    private fun updateTotalMoney(){
        totalMoney  = 0
        listCart.forEach {
            totalMoney += (it.product.price * it.amount)
        }
        val formatter = DecimalFormat("#,###")
        Log.e("======>", formatter.format(totalMoney) )
        tv_total_money.text = formatter.format(totalMoney)
    }
}