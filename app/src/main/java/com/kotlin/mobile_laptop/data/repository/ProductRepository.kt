package com.kotlin.mobile_laptop.data.repository
import com.kotlin.mobile_laptop.data.remote.ProductRemoteData
import com.kotlin.mobile_laptop.model.ItemMenuResponse
import com.kotlin.mobile_laptop.model.OrderResponse
import com.kotlin.mobile_laptop.model.ProductResponse
import io.reactivex.rxjava3.core.Single

//tổng hợp data product

class ProductRepository (
    private val remoteData: ProductRemoteData = ProductRemoteData(),
        ){


    fun getItemMenu(): Single<ItemMenuResponse> {
        return remoteData.getItemMenu()
    }

    fun getProduct() : Single<ProductResponse> {
        return remoteData.getProduct()
    }

    fun getDetail(type : Int): Single<ProductResponse>{
        return  remoteData.getDetail(type)
    }

    fun oder(
        email: String,
        phoneNumber: String,
        address: String,
        amount: Int,
        totalMoney: String,
        idUser: String,
        detail: String
    ) : Single<OrderResponse>{
        return remoteData.oder(
            email = email,
            phoneNumber = phoneNumber,
            address = address,
            amount = amount,
            totalMoney = totalMoney,
            idUser = idUser,
            detail = detail)
    }
}