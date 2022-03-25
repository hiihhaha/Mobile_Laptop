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
import kotlinx.android.synthetic.main.activity_register_account.*

class RegisterAccountActivity : AppCompatActivity() {
    var apiBanHang = Cilent.getInstance(Utils.BaseUrl)?.create(ApiApp::class.java)
    var userName = ""
    var password = ""
    var confirmpassword = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_account)
        initControll()
    }
    private fun initControll() {
        btn_register.setOnClickListener {

            if (isValidateSuccess()) {
                register()
            }

        }

    }
    private fun isValidateSuccess(): Boolean {

        password = edt_password.text.toString().trim()
        confirmpassword = edt_confirmpassword.text.toString().trim()
        userName = edt_username.text.toString().trim()

        when {

            (TextUtils.isEmpty(password)) -> {
                Toast.makeText(
                    this,
                    "Bạn chưa nhập mật khẩu",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            (TextUtils.isEmpty(confirmpassword)) -> {
                Toast.makeText(
                    this,
                    "Vui lòng nhập lại mật khẩu",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }

            (TextUtils.isEmpty(userName)) -> {
                Toast.makeText(
                    this,
                    "Bạn chưa nhập tên đăng ký",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            (password != confirmpassword) -> {
                Toast.makeText(
                    this,
                    "Mật khẩu không trùng nhau",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            else -> return true
        }
    }
    private fun register() {
        apiBanHang?.register(userName,password)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<UserResponse> {
                override fun onSuccess(userResponse: UserResponse) {
                    if (userResponse.success == true) {
                        userResponse.result?.getOrNull(0)?.let {
                            // Lưu giá trị user mà server trả về cho biến static user
                            Utils.user = it
                        }

                        Toast.makeText(
                            this@RegisterAccountActivity,
                            "Đăng ký thành công",
                            Toast.LENGTH_SHORT
                        ).show()
                        var intent = Intent(this@RegisterAccountActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@RegisterAccountActivity,
                            userResponse.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

                override fun onError(e: Throwable) {
                    Log.e("======>", "onError: ${e.message} " )
                    Toast.makeText(this@RegisterAccountActivity, e.message, Toast.LENGTH_SHORT).show()
                }

                override fun onSubscribe(d: Disposable) {
                }
            })
    }
}