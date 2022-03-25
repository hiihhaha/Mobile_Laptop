package com.kotlin.mobile_laptop.ui.pay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.gson.Gson
import com.kotlin.mobile_laptop.R
import com.kotlin.mobile_laptop.model.CartControler
import com.kotlin.mobile_laptop.model.OrderResponse
import com.kotlin.mobile_laptop.retrofit.ApiApp
import com.kotlin.mobile_laptop.retrofit.Cilent
import com.kotlin.mobile_laptop.ui.home.HomeActivity
import com.kotlin.mobile_laptop.utils.Utils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_pay.*
import java.text.DecimalFormat

class PayActivity : AppCompatActivity() {
    var apiBanHang = Cilent.getInstance(Utils.BaseUrl)?.create(ApiApp::class.java)
    var totalMoney = 0
    var email = ""
    var address = ""
    var phoneNumber = ""
    var idUser = ""
    var amount = 0
    var detailoder = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)
        totalMoney = intent.getIntExtra("totalMoney",0)
        countItem()
        initView()
        initControl ()
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
    private fun initControl (){
        img_back3.setOnClickListener{onBackPressed()}
        btn_Pay.setOnClickListener {
            if (isValidateSuccess()){
                payOrder()


            }
        }
    }
    private fun isValidateSuccess() : Boolean {
        address = edt_address.text.toString().trim()
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this,"Bạn chưa nhập địa chỉ", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    private fun payOrder() {
        apiBanHang?.oder(email,phoneNumber,address,amount,totalMoney.toString(),idUser, Gson().toJson(CartControler.arrayCart))
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<OrderResponse> {
                override fun onSuccess(oderResponse: OrderResponse) {
                    if (oderResponse.success == true) {
                        Toast.makeText(
                            this@PayActivity,
                            "Thanh toán thành công",
                            Toast.LENGTH_SHORT
                        ).show()
                        CartControler.arrayCart.clear()
                        btn_Pay.setOnClickListener {
                            var intent = Intent(this@PayActivity, HomeActivity::class.java)
                            startActivity(intent)
                        }
                        finish()
                    } else {
                        Toast.makeText(
                            this@PayActivity,
                            oderResponse.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(this@PayActivity, e.message, Toast.LENGTH_SHORT).show()
                }

                override fun onSubscribe(d: Disposable) {
                }
            })
    }
}