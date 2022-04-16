package com.kotlin.mobile_laptop.ui.register

import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.kotlin.mobile_laptop.R
import com.kotlin.mobile_laptop.base.BaseActivity
import com.kotlin.mobile_laptop.data.local.AppPreferences
import com.kotlin.mobile_laptop.ui.home.HomeActivity
import kotlinx.android.synthetic.main.activity_register_account.*

class RegisterAccountActivity : BaseActivity() {
    private val viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
    var userName = ""
    var password = ""
    var confirmpassword = ""
    override val layoutId: Int = R.layout.activity_register_account

    override fun setupView() {

    }


    override fun setupObserver() {
        viewModel.userResponseLiveData.observe(this, {
            if (it.success == true) {
                it.result?.getOrNull(0)?.let { user ->
                    viewModel.saveUserDataLocal(user)

                    val intent =
                        Intent(this@RegisterAccountActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } ?: kotlin.run {
                    Toast.makeText(
                        this@RegisterAccountActivity,
                        "Thông tin người dùng trống ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun setupEvent() {
        btn_register.setOnClickListener {
            if (isValidateSuccess()) {
                viewModel.register(userName, password)
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
}





