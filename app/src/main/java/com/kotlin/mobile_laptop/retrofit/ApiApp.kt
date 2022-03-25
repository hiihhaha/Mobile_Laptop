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
        @POST("donhang.php")
        fun oder(
                @Field("email") email: String,
                @Field("phoneNumber") phoneNumber: String,
                @Field("address") address: String,
                @Field("amount") amount: Int,
                @Field("totalMoney") totalMoney: String,
                @Field("idUser") idUser: String,
                @Field("detail") detail: String,
        ) : Single<OrderResponse>

        @FormUrlEncoded
        @POST("login.php")
        fun login(
                @Field("username") userName : String,
                @Field("password") password : String
        ) : Single<UserResponse>

        @FormUrlEncoded
        @POST("register.php")
        fun register(
                @Field("username") userName : String,
                @Field("password") password : String
        ) : Single<UserResponse>

        @FormUrlEncoded
        @POST("resetPass.php")
        fun resetPass(
                @Field("email") email : String,
        ) : Single<UserResponse>
}