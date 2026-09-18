package com.example.wastewar.ui.user.admin

import retrofit2.Response
import retrofit2.http.GET

interface AdminApiInterface {

    @GET("api/admin/get-sales-item")
    suspend fun getSaleItem()  : Response<SalesItemRes>

    @GET("api/admin/get-centres")
    suspend fun getCentres() : Response<CentreRes>







}