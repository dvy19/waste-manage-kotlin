package com.example.wastewar.ui.user.admin

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface AdminApiInterface {

    @GET("api/admin/get-sales-items")
    suspend fun getSaleItem()  : Response<SalesItemRes>

    @GET("api/admin/get-centres")
    suspend fun getCentres() : Response<CentreRes>


    @POST("api/admin/get-sales-item/{id}")
    suspend fun saleItemDetails(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : Response<SingleSaleItem>







}