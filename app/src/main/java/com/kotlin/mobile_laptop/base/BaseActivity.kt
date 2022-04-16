package com.kotlin.mobile_laptop.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        setupView()
        setupObserver()
        setupEvent()
    }

//  khai báo layout id
    protected abstract val layoutId: Int

    // thằng này để setup các loại view lúc mới vào
    protected abstract fun setupView()

    // setup hứng data từ viewmodel
    protected abstract fun setupObserver()

    // setup các sự kiện click
    protected abstract fun setupEvent()
}