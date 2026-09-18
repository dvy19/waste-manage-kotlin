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
    val item:ItemData
)

data class ItemData(
    val image:String?,
    val name:String,
    val quantity:String,
    val weight:String,
    val category: String,
    val status:String,
    val processingMethod:String,
    val trackingId:String
)