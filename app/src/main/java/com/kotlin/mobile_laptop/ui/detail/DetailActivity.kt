package com.kotlin.mobile_laptop.ui.detail

import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.kotlin.mobile_laptop.R
import com.kotlin.mobile_laptop.base.BaseActivity
import com.kotlin.mobile_laptop.model.CartControler
import com.kotlin.mobile_laptop.model.Product
import com.kotlin.mobile_laptop.ui.cart.CartActivity
import kotlinx.android.synthetic.main.activity_detail.*
import java.text.DecimalFormat

class DetailActivity : BaseActivity() {
    var cartControler = CartControler ()
    var product : Product? = null
    var amount = 1




    override val layoutId: Int = R.layout.activity_detail

    override fun setupView() {
        product = intent.getSerializableExtra("mobilePhone") as Product?
        setupSpinner()
        product?.let {
            showProduct(it)
        }
    }

    override fun setupObserver() {

    }

    override fun setupEvent() {
        initControl()
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