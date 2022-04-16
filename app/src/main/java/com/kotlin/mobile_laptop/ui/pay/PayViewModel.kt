package com.kotlin.mobile_laptop.ui.pay

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.mobile_laptop.data.repository.ProductRepository
import com.kotlin.mobile_laptop.model.OrderResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class PayViewModel(
    private val payResponse: ProductRepository = ProductRepository()
) : ViewModel() {
    val payLiveData = MutableLiveData<OrderResponse>()
    val errorLiveData = MutableLiveData<String>()

    fun oder(
        email: String,
        phoneNumber: String,
        address: String,
        amount: Int,
        totalMoney: String,
        idUser: String,
        detail: String
    ) {
        payResponse.oder(
            email,
            phoneNumber,
            address,
            amount,
            totalMoney,
            idUser,
            detail
        )
            .subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<OrderResponse> {
                override fun onSuccess(orderResponse: OrderResponse) {
                    payLiveData.postValue(orderResponse)
                }

                override fun onError(e: Throwable) {
                    errorLiveData.postValue(e.message)
                }

                override fun onSubscribe(d: Disposable) {
                }
            })
    }


}