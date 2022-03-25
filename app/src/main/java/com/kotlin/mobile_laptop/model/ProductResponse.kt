package com.kotlin.mobile_laptop.model

import java.io.Serializable

class ProductResponse (
    var success : Boolean?,
    var message : String?,
    var result : List<Product>?
)

class Product(
    var id : Int,
    var name : String,
    var price : Int,
    var image : String,
    var description : String
) : Serializable