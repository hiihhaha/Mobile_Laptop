package com.kotlin.mobile_laptop.ui.pay

import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.kotlin.mobile_laptop.R
import com.kotlin.mobile_laptop.base.BaseActivity
import com.kotlin.mobile_laptop.model.CartControler
import com.kotlin.mobile_laptop.ui.home.HomeActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_pay.*
import java.text.DecimalFormat

class PayActivity : BaseActivity() {
    private val viewModel by lazy {
        ViewModelProvider(this)[PayViewModel::class.java]
    }
    var totalMoney = 0
    var email = ""
    var address = ""
    var phoneNumber = ""
    var idUser = ""
    var detail = ""
    var amount = 0


    override val layoutId: Int = R.layout.activity_pay

    override fun setupView() {
        totalMoney = intent.getIntExtra("totalMoney", 0)
        countItem()
        initView()



    }

    override fun setupObserver() {
        viewModel.payLiveData.observe(this, { orderResponse ->
            if (orderResponse.success == true) {
                Toast.makeText(this@PayActivity, "Thanh toán thành công", Toast.LENGTH_SHORT).show()
                CartControler.arrayCart.clear()
                    var intent = Intent(this@PayActivity, HomeActivity::class.java)
                    startActivity(intent)

                finish()
            } else {
                Toast.makeText(this@PayActivity, orderResponse.message, Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.errorLiveData.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }


    override fun setupEvent() {
        img_back3.setOnClickListener { onBackPressed() }
        btn_Pay.setOnClickListener {
            if (isValidateSuccess()) {
                viewModel.oder(
                    email = email,
                    phoneNumber = phoneNumber,
                    address = address,
                    amount = amount,
                    totalMoney = totalMoney.toString(),
                    idUser = idUser,
                    detail = detail
                )
            }
        }
    }

    private fun countItem() {
        CartControler.arrayCart.forEach {
            amount += it.amount
        }

    }

    private fun initView() {
        val formatter = DecimalFormat("#,###")
        val giaSP = formatter.format(totalMoney)
        tv_Money.text = "$giaSP Đ"
    }

    private fun isValidateSuccess(): Boolean {
        address = edt_address.text.toString().trim()
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Bạn chưa nhập địa chỉ", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}
