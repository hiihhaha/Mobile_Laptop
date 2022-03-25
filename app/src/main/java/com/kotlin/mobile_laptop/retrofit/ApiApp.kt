package com.kotlin.mobile_laptop.retrofit

import com.kotlin.mobile_laptop.model.ItemMenuResponse
import com.kotlin.mobile_laptop.model.OrderProduct
import com.kotlin.mobile_laptop.model.OrderResponse
import com.kotlin.mobile_laptop.model.ProductResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface ApiApp {
        @GET("getItemMenu.php")
        fun getItemMenu(): Single<ItemMenuResponse>

        @GET ("getProduct.php")
        fun getProduct() : Single<ProductResponse>

        @GET("getDetail.php")
        fun getDetail(
                @Query("type") type : Int
        ): Single<ProductResponse>
        @FormUrlEncoded
        @POST("oder.php")
        fun oder(
                @Field("email") email: String,
                @Field("phoneNumber") phoneNumber: String,
                @Field("address") address: String,
                @Field("amount") amount: Int,
                @Field("totalMoney") totalMoney: String,
                @Field("idUser") idUser: String,
                @Field("detai") detail: String,
        ) : Single<OrderResponse>
}