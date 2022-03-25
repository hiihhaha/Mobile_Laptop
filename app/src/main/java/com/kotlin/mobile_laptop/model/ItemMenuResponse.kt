package com.kotlin.mobile_laptop.model

class ItemMenuResponse(
    var success: Boolean?,
    var message: String?,
    var result: List<ItemMenu>?
)

class ItemMenu(
    var id: Int?,
    var name: String?,
    var image: String?
)