package com.kotlin.mobile_laptop.data.local

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.kotlin.mobile_laptop.App
import com.kotlin.mobile_laptop.model.User

class AppPreferences{
    var preferences: SharedPreferences
    private var editor: SharedPreferences.Editor

    val REFERENCES_NAME = "AppPreferences"
    val USER_ID = "user_id"
    val EMAIL = "email"
    val PASSWORD = "password"
    val USER_NAME = "username"
    val PHONE_NUMBER = "phoneNumber"


    init {
        preferences = App.getInstance()
            .getSharedPreferences(REFERENCES_NAME, Context.MODE_PRIVATE)
        editor = preferences.edit()
    }

    /**
     *  Lưu lại thông tin người dùng ở local
     * */
    fun saveUserInfo(user: User) {
        editor.apply {
            putString(USER_ID, user.id)
            putString(EMAIL, user.email)
            putString(PASSWORD, user.password)
            putString(USER_NAME, user.username)
            putString(PHONE_NUMBER, user.phoneNumber)
        }.apply()
    }

    /**
     *  lấy ra thông tin người dùng ở local
     * */
    fun getUserInfo(): User {
        return User(
            id = preferences.getString(USER_ID, null),
            email = preferences.getString(EMAIL, null),
            password = preferences.getString(PASSWORD, null),
            username = preferences.getString(USER_NAME, null),
            phoneNumber = preferences.getString(PHONE_NUMBER, null),
        )
    }

}