package com.example.wastewar.ui.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterInterface {

    @POST("api/user/register")
    suspend fun register(
        @Body registerReq: RegisterReq
    ): Response<RegisterRes>

    @POST("api/user/login")
    suspend fun login(
        @Body loginReq: LoginReq
    ): Response<RegisterRes>


}