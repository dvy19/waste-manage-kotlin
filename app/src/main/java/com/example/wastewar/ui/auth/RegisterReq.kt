package com.example.wastewar.ui.auth

data class RegisterReq(

    var name: String,
    var role:String,
    var password:String,
    var email:String
)

data class RegisterRes(

    var message:String,
    var token:String,
    var user:UserData
)

data class UserData(
    var id:String,
    var name:String,
    var email:String,
    var role:String
)



data class LoginReq(
    var email: String,
    var password:String
)

/*
 message:"user registered successfully",
            user:{
                email:user.email,
                role:user.role,
                id:user._id,
                name:user.name
            },
            token:accessToken
 */