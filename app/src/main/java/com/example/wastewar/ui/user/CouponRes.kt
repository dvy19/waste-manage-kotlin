package com.example.wastewar.ui.user

data class CouponRes (
    var message:String,
    var coupon:CouponData
)

data class CouponData(
    var code:String,
    var discount:Int,
    var isUsed:Boolean,
    var user:String,
    var _id:String,
)
