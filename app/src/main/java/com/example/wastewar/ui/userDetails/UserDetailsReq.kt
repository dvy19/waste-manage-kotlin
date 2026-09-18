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

data class ProfileRes(
    val message:String,
    val Profile:ProfileData
)

data class ProfileData(
    val phoneNumber:String,
    val city:String,
    val pinCode:String,
    val address: String,
    val coordinates: List<Double>,
    val createdAt:String,
    val updatedAt: String,
    val _id:String,

    val user:UserData
)

data class UserData(
    val email:String,
    val name:String,
    val role:String,
    val _id:String,
    val password:String,
    val createdAt:String,
    val updatedAt: String,
)
