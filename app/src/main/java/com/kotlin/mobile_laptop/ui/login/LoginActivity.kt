package com.kotlin.mobile_laptop.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.kotlin.mobile_laptop.R
import com.kotlin.mobile_laptop.model.UserResponse
import com.kotlin.mobile_laptop.retrofit.ApiApp
import com.kotlin.mobile_laptop.retrofit.Cilent
import com.kotlin.mobile_laptop.ui.home.HomeActivity
import com.kotlin.mobile_laptop.utils.Utils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    var apiBanHang = Cilent.getInstance(Utils.BaseUrl)?.create(ApiApp::class.java)
    var userName = ""
    var password = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initControl()
    }

    private fun initControl() {
        btn_login.setOnClickListener {
            if (isValidateSuccess()) {
                login()
            }
        }

        tv_register.setOnClickListener {
            startActivity(Intent(this, RegisterAccountActivity::class.java))
            finish()
        }
        tv_forgot.setOnClickListener {
            startActivity(Intent(this,RegisterAccountActivity::class.java))
            finish()
        }
    }
    private fun isValidateSuccess(): Boolean {
        userName = edt_username.text.toString().trim()
        password = edt_password.text.toString().trim()
        when {
            (TextUtils.isEmpty(userName)) -> {
                Toast.makeText(
                    this,
                    "Bạn chưa nhập User name",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            (TextUtils.isEmpty(password)) -> {
                Toast.makeText(
                    this,
                    "Bạn chưa nhập mật khẩu",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
        }
        return true
    }
    private fun login() {
        apiBanHang?.login(userName,password)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<UserResponse> {
                override fun onSuccess(userResponse: UserResponse) {
                    if (userResponse.success == true) {
                        userResponse.result?.getOrNull(0)?.let {
                            Utils.user = it
                        }
                        Toast.makeText(
                            this@LoginActivity,
                            "Đăng nhập thành công",
                            Toast.LENGTH_SHORT
                        ).show()
                        var intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            userResponse.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onError(e: Throwable) {
                    Log.e("======>", "onError: ${e.message} " )
                    Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()
                }

                override fun onSubscribe(d: Disposable) {
                }
            })
    }
}