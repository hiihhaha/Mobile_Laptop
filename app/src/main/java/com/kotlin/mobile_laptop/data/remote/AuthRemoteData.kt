package com.kotlin.mobile_laptop.data.remote

import com.kotlin.mobile_laptop.data.remote.retrofit.ApiApp
import com.kotlin.mobile_laptop.data.remote.retrofit.Cilent
import com.kotlin.mobile_laptop.model.UserResponse
import com.kotlin.mobile_laptop.utils.Utils
import io.reactivex.rxjava3.core.Single

// Chỗ này để lấy  data user từ server
class AuthRemoteData() {
    private val apiApp = Cilent.getInstance(Utils.BaseUrl)!!.create(ApiApp::class.java)

    fun login (userName : String , password : String): Single<UserResponse> {
        return apiApp.login(userName,password)
    }

    fun register (userName : String , password : String): Single<UserResponse> {
        return apiApp.register(userName,password)
    }

}