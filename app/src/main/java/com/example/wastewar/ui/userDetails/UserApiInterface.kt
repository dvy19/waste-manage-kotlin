package com.example.wastewar.ui.userDetails

import com.example.wastewar.ui.user.CouponRes
import retrofit2.http.POST



import retrofit2.http.Query
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.Part
import retrofit2.http.Path


interface UserApiInterface {


    @Multipart
    @POST("api/user/create-profile")
    suspend fun createProfile(

        @Header("Authorization") token: String,
        @Part("phoneNumber") phoneNumber: RequestBody,
        @Part("city") city: RequestBody,
        @Part("pinCode") pinCode: RequestBody,
        @Part("address") address: RequestBody,
        @Part("coordinates") coordinates: RequestBody,
        @Part("profile") profile: MultipartBody.Part?


    ) : Response<UserDetailsRes>

    @GET("api/user/get-profile")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ) : Response<ProfileRes>


    @GET("api/item/get-user-stats")
    suspend fun getUserStats(
        @Header("Authorization") token: String
    ) : Response<UserStats>



    @POST("api/item/create-coupons")
    suspend fun createCoupon(
        @Header("Authorization") token: String,
    ): Response<CouponRes>







}