package com.kotlin.mobile_laptop.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.mobile_laptop.data.repository.ProductRepository
import com.kotlin.mobile_laptop.model.ItemMenuResponse
import com.kotlin.mobile_laptop.model.ProductResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeViewModel(
    private var productResponse: ProductRepository = ProductRepository()
        ) : ViewModel(){
    var itemMenuResponseLiveData = MutableLiveData<ItemMenuResponse>()
    var productResponseLiveData = MutableLiveData<ProductResponse>()
    var errorLiveData = MutableLiveData<String>()

    fun getItemMenu () {
        productResponse.getItemMenu()
            .subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<ItemMenuResponse> {
                override fun onSubscribe(d: Disposable?) {


                }

                override fun onSuccess(response: ItemMenuResponse?) {
                    itemMenuResponseLiveData.postValue(response)
                }

                override fun onError(response: Throwable?) {
                    errorLiveData.postValue(response?.message)
                }

            })
    }

    fun getListProduct(){
        productResponse.getProduct()
            .subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<ProductResponse> {
                override fun onSuccess(productResponse: ProductResponse) {
                    productResponseLiveData.postValue(productResponse)
                }

                override fun onSubscribe(d: Disposable?) {
                }

                override fun onError(e: Throwable?) {
                    errorLiveData.postValue(e?.message)
                }
            })
    }

}