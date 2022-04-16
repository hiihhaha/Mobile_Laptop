package com.kotlin.mobile_laptop.data.local.auth

import com.kotlin.mobile_laptop.App
import com.kotlin.mobile_laptop.data.local.AppPreferences
import com.kotlin.mobile_laptop.model.User

class AuthLocalData {
    // Chỗ này để lấy  data từ local
   private val appPreferences =  AppPreferences()

    fun saveUserInfo(user: User){
        appPreferences.saveUserInfo(user)
    }

    fun getUserInfo(): User {
        return appPreferences.getUserInfo()
    }
}