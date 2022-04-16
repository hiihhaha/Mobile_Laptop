package com.kotlin.mobile_laptop.ui.login

import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.kaopiz.kprogresshud.KProgressHUD
import com.kotlin.mobile_laptop.R
import com.kotlin.mobile_laptop.base.BaseActivity
import com.kotlin.mobile_laptop.data.local.AppPreferences
import com.kotlin.mobile_laptop.ui.home.HomeActivity
import com.kotlin.mobile_laptop.ui.register.RegisterAccountActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    var userName = ""
    var password = ""

    val processDialog by lazy {
        KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Please wait")
            .setCancellable(true)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
    }

    override val layoutId: Int = R.layout.activity_login

    override fun setupView() {

    }

    override fun setupObserver() {
        viewModel.userResponseLiveData.observe(this, {
            processDialog.dismiss()
            if (it.success == true) {
                // Thông báo đăng nhập thành công
                Toast.makeText(
                    this@LoginActivity,
                    "Đăng nhập thành công",
                    Toast.LENGTH_SHORT
                ).show()

                // Kiểm tra thông tin người dùng
                it.result?.getOrNull(0)?.let { user ->

                    // Nếu có : Lưu lại thông tin người dùng
                    viewModel.saveUserDataLocal(user)

                    // Chuyển màn hình
                    Intent(this@LoginActivity, HomeActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }

                } ?: kotlin.run {
                    Toast.makeText(
                        this@LoginActivity,
                        "Thông tin người dùng trống ",
                        Toast.LENGTH_SHORT
                    ).show()
                }


            } else {

                Toast.makeText(
                    this@LoginActivity,
                    it.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })


        viewModel.errorLiveData.observe(this, { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        })
    }

    override fun setupEvent() {
        btn_login.setOnClickListener {

            if (isValidateSuccess()) {
                processDialog.show()
                viewModel.login(userName, password)
            }
        }

        tv_register.setOnClickListener {
            startActivity(Intent(this, RegisterAccountActivity::class.java))
            finish()
        }
        tv_forgot.setOnClickListener {
            startActivity(Intent(this, RegisterAccountActivity::class.java))
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


}