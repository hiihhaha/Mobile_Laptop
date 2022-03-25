package com.kotlin.mobile_laptop.model

class OrderProduct (
    var product: Product,
    var amount : Int,
)

class CartControler() {

    fun addProduct(product: Product, amount: Int = 1){
        if (arrayCart.isEmpty()){
            arrayCart.add(
                OrderProduct(
                    product = product,
                    amount = 1
                ))
            return
        }
        arrayCart.forEach{
            if(it.product.id == product.id){
                it.amount += amount
                return
            }
        }
        arrayCart.add(
            OrderProduct(
                product = product,
                amount = amount
            ))
        return
    }

    fun getArrayCart() : ArrayList<OrderProduct> {
        return arrayCart
    }


    companion object{
        val arrayCart = arrayListOf<OrderProduct>()
    }
}