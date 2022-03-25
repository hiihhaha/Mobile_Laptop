package com.kotlin.mobile_laptop.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.kotlin.mobile_laptop.R
import com.kotlin.mobile_laptop.model.UserResponse
import com.kotlin.mobile_laptop.retrofit.ApiApp
import com.kotlin.mobile_laptop.retrofit.Cilent
import com.kotlin.mobile_laptop.utils.Utils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_reset_pass.*

class ResetPassActivity : AppCompatActivity() {
    var apiBanHang = Cilent.getInstance(Utils.BaseUrl)?.create(ApiApp::class.java)
    var email = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pass)
        initControll()
    }
    private fun initControll() {
        btn_rspass.setOnClickListener {
            if (isValidateSuccess()) {
                progressbar.visibility = View.VISIBLE
                resetPass()



            }
        }
    }
    private fun isValidateSuccess(): Boolean {
        email = edt_email.text.toString().trim()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(
                this,
                "Bạn chưa nhập email",
                Toast.LENGTH_SHORT)
            return false
        }

        return true
    }
    private fun resetPass() {
        apiBanHang?.resetPass(email)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<UserResponse> {
                override fun onSuccess(userResponse: UserResponse) {
                    progressbar.visibility = View.GONE
                    if (userResponse.success == true) {
                        userResponse.result?.getOrNull(0)?.let {
                            Utils.user = it

                        }
                        Toast.makeText(
                            this@ResetPassActivity,
                            "reset pass thành công",
                            Toast.LENGTH_SHORT
                        ).show()
                        var intent = Intent(this@ResetPassActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@ResetPassActivity,
                            userResponse.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onError(e: Throwable) {
                    progressbar.visibility = View.GONE
                    Toast.makeText(this@ResetPassActivity, e.message, Toast.LENGTH_SHORT).show()
                }

                override fun onSubscribe(d: Disposable) {
                }
            })


    }
}