package com.example.wastewar.ui.user.item


data class AddItemReq(
    val image:String?,
    val name:String,
    val quantity:String,
    val weight:String,
    val category: String
)

data class AddItemRes(
    val message:String,
    val item:List<ItemData>
)

data class ItemData(
    val image:String?,
    val name:String,
    val quantity:String,
    val weight:String,
    val category: String,
    val status:String,
    val processingMethod:String,
    val trackingId:String,
    val createdAt:String,
    val updatedAt:String,
    val __v:Int,
    val _id:String
)


data class CartItemRes(
    var message:String,
    var orders:List<CartItemsData>
)
data class CartItemsData(
    var _id:String,
    var idempotencyKey: String,
    var user:String,
    var item:String,
    var quantity:String,
    var amount:String,
    var coupon: String,
    var image:String?
)