package com.example.wastewar.ui.user.item

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface ItemApiInterface{

    @Multipart
    @POST("api/item/create-item")
    suspend fun addItem(

        @Header("Authorization") token: String,
        @Part("name") name:String,
        @Part("quantity") quantity:String,
        @Part("weight") weight:String,
        @Part("category") category:String,
        @Part image:MultipartBody.Part?

    ) : Response<AddItemRes>


}
