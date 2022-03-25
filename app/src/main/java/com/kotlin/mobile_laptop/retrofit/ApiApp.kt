package com.kotlin.mobile_laptop.retrofit

import com.kotlin.mobile_laptop.model.*
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

        @FormUrlEncoded
        @POST("login.php")
        fun login(
                @Field("userName") userName : String,
                @Field("password") password : String,
        ) : Single<UserResponse>

        @FormUrlEncoded
        @POST("register.php")
        fun register(
                @Field("userName") userName : String,
                @Field("password") password : String,
                @Field("confirmpassword") confirmpassword : String
        ) : Single<UserResponse>
}