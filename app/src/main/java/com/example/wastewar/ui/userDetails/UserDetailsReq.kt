package com.example.wastewar.ui.userDetails


data class UserDetailsReq(

    var phoneNumber:String,
    var city:String,
    var pinCode:String,
    var address: String,
    var coordinates: List<Double>,
    var profile:String?
)


data class UserDetailsRes(
    val message: String,
    var data: UserDetailsReq
)