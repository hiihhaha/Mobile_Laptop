package com.kotlin.mobile_laptop.data.repository

import com.kotlin.mobile_laptop.data.local.auth.AuthLocalData
import com.kotlin.mobile_laptop.data.remote.AuthRemoteData
import com.kotlin.mobile_laptop.model.User
import com.kotlin.mobile_laptop.model.UserResponse
import io.reactivex.rxjava3.core.Single

// Thằng này để tổng hợp data user
class AuthRepository(
    private val remoteData: AuthRemoteData = AuthRemoteData(),
    private val localeData: AuthLocalData = AuthLocalData()
) {

    fun login (userName : String , password : String): Single<UserResponse> {
        return remoteData.login(userName,password)
    }

    fun register (userName : String , password : String): Single<UserResponse> {
        return remoteData.register(userName,password)
    }

    fun saveUserDataLocal(user : User){
        localeData.saveUserInfo(user)
    }

    fun getUserDataLocal(): User {
        return localeData.getUserInfo()
    }
}