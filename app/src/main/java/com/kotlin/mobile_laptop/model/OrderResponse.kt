package com.kotlin.mobile_laptop.model

import java.io.Serializable

class OrderResponse (
    var success : Boolean?,
    var message : String?,
    var result : List<Order>?
        )
class Order(
    var id : Int,
    var idUser : String,
    var address : String,
    var phoneNumber :String,
    var totalMoney : String

) :Serializable