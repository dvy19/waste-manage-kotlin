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
    val Profile:ProfileData?
)

data class ProfileData(
    val address: String,
    val city: String,
    val coordinates: List<Double>,
    val createdAt: String,
    val phoneNumber: String,
    val pinCode: String,
    val profile: String,
    val updatedAt: String,
    val user: UserData,
    val __v: Int,
    val _id: String
)

data class UserData(
    val _id: String,
    val role: String,
    val name: String,
    val email: String,
    val password: String
)


data class UserStats(
    val message: String,
    val stats:StatsData
)

data class StatsData(
    val itemsAdded: Int,
    val points: Int,
    val user:String,
    val __v: Int,
    val _id:String
)

