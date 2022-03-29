package com.kotlin.mobile_laptop

import android.app.Application

class AppMobileLaptop : Application() {

    companion object{
        @Volatile
        private var instance: AppMobileLaptop? = null

        @JvmStatic
        fun getInstance(): AppMobileLaptop = instance ?: synchronized(this) {
            instance ?: AppMobileLaptop().also {
                instance = it
            }
        }
    }
}