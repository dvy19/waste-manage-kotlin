package com.example.wastewar.ui.user.item

import com.example.wastewar.ui.user.AiSuggestionResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path


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

    @POST("api/item/get-item-id/{trackingId}")
    suspend fun trackItem(
        @Header("Authorization") token: String,
        @Path("trackingId") trackingId:String
    ) : Response<AddItemRes>


    @GET("api/user//get-user-orders")
    suspend fun getUserCart(
        @Header("Authorization") token: String
    ) : Response<CartItemRes>

    @Multipart
    @POST("api/item/analyze-waste")
    suspend fun analyzeWaste(
        @Header("Authorization") token: String,
        @Part("image") image:MultipartBody.Part?
    ) : Response<AiSuggestionResponse>







}
