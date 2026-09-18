package com.example.wastewar.ui.auth

import com.example.wastewar.ui.ApiClient
import retrofit2.Response

class AuthRepo {

    val api=ApiClient.registerApi

    suspend fun registerUser(req: RegisterReq):Response<RegisterRes>{

        return api.register(req)
    }

    suspend fun loginUser(req: LoginReq):Response<RegisterRes>{
        return api.login(req)
    }



}