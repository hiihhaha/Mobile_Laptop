package com.kotlin.mobile_laptop.data.remote

import com.kotlin.mobile_laptop.data.remote.retrofit.ApiApp
import com.kotlin.mobile_laptop.data.remote.retrofit.Cilent
import com.kotlin.mobile_laptop.model.ItemMenuResponse
import com.kotlin.mobile_laptop.model.OrderResponse
import com.kotlin.mobile_laptop.model.ProductResponse
import com.kotlin.mobile_laptop.utils.Utils
import io.reactivex.rxjava3.core.Single
// lấy dữ liệu sản phẩm từ sv về
class ProductRemoteData {

    private val apiApp = Cilent.getInstance(Utils.BaseUrl)!!.create(ApiApp::class.java)

    fun getItemMenu(): Single<ItemMenuResponse> {
        return apiApp.getItemMenu()
    }

    fun getProduct() : Single<ProductResponse> {
        return apiApp.getProduct()
    }

    fun getDetail(type : Int): Single<ProductResponse>{
        return  apiApp.getDetail(type)
    }

    fun oder(
        email : String,
        phoneNumber : String,
        address : String,
        amount : Int,
        totalMoney : String,
        idUser : String,
        detail : String
    ) : Single<OrderResponse>{
        return apiApp.oder(
            email = email,
            phoneNumber = phoneNumber,
            address = address,
            amount = amount,
            totalMoney = totalMoney,
            idUser = idUser,
            detail = detail)
    }
}