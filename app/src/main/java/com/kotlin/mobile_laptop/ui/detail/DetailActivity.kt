package com.kotlin.mobile_laptop.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.kotlin.mobile_laptop.R
import com.kotlin.mobile_laptop.model.CartControler
import com.kotlin.mobile_laptop.model.Product
import com.kotlin.mobile_laptop.ui.cart.CartActivity
import kotlinx.android.synthetic.main.activity_detail.*
import java.text.DecimalFormat

class DetailActivity : AppCompatActivity() {
    var cartControler = CartControler ()
    var product : Product? = null
    var amount = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        product = intent.getSerializableExtra("mobilePhone") as Product?
        initControl()
        setupSpinner()
        product?.let {
            showProduct(it)
        }
    }

    private fun initControl() {
        img_cart.setOnClickListener {
            var intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
        img_back.setOnClickListener { onBackPressed() }
        btn_add.setOnClickListener {
            product?.let { cartControler.addProduct(it, amount) }
            updateUICart()
        }
    }

    override fun onResume() {
        super.onResume()
        updateUICart()
    }

    private fun setupSpinner() {
        val listAmount = arrayListOf<Int>()
        for (i in 1..10) {
            listAmount.add(i)
        }
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, listAmount)
        spinner.adapter = adapterSpinner
        spinner.setSelection(0)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                amount = listAmount[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }


    private fun showProduct(product: Product) {
        tv_name.text = product.name
        tv_Description.text = product.description
        Glide.with(this).load(product.image).into(img_Product)
        val formatter = DecimalFormat("#,###")
        val giaSP = formatter.format(product.price)
        tv_price.text = "Giá : $giaSP Đ"
    }
    private fun updateUICart() {
        var soLuong = 0
        CartControler.arrayCart.forEach {
            soLuong += it.amount
        }
        tv_Amount.isVisible = soLuong > 0
        tv_Amount.text = soLuong.toString()
    }
}