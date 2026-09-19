package com.example.wastewar.ui.user.admin
data class SalesItemRes(
    val message: String,
    val items: List<SaleItemData>
)

data class SaleItemData(
    val _id: String,
    val name: String,
    val image: String?,
    val price: Int,
    val quantity: Int,
    val manufacturer: String,
    val about: String,
    val materials: List<String>,
    val tag: String
)

data class CentreRes(
    var message:String,
    var centres:List<CentreData>
)

data class CentreData(
    var _id:String,
    var name:String?,
    var about:String?,
    var owner:String,
    var image:String?,
    var material:String,
    var coordinates:List<Double>,
    var pinCode:String,
    var contact:String,
    var address:String


)