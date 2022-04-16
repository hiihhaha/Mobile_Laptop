package com.kotlin.mobile_laptop.ui.type_product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.mobile_laptop.data.repository.ProductRepository
import com.kotlin.mobile_laptop.model.ProductResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class TyprProductViewModel(
    private var typeProductResponse: ProductRepository = ProductRepository()
) : ViewModel() {
    val typeProductLiveData = MutableLiveData<ProductResponse>()
    val errorLiveData = MutableLiveData<String>()

    fun getDetail(type: Int) {
        typeProductResponse.getDetail(type)
            .subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<ProductResponse> {
                override fun onSuccess(productResponse: ProductResponse) {
                    typeProductLiveData.postValue(productResponse)
                }

                override fun onError(e: Throwable) {
                    errorLiveData.postValue(e.message)


                }

                override fun onSubscribe(d: Disposable) {}

            })
    }
}
