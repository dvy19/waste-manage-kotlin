package com.example.wastewar.ui.user.item

import com.example.wastewar.ui.ApiClient
import com.example.wastewar.ui.auth.SessionManager
import com.example.wastewar.ui.user.AiSuggestionResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class AddItemRepo(
    private val sessionManager: SessionManager
) {


    val api= ApiClient.addItemApi

    val token=sessionManager.getAccessToken()


    fun String.toPlainText(): RequestBody {
        return this.toRequestBody("text/plain".toMediaType())
    }

    suspend fun addItem(
        name:String,
        quantity:String,
        weight:String,
        category:String,
        image:MultipartBody.Part?
    ): Response<AddItemRes>{

        return api.addItem(
            token = "Bearer $token",
            name = name,
            quantity = quantity,
            weight = weight,
            category = category,
            image = image,
        )
    }

    suspend fun trackItem(trackingId:String) : Response<AddItemRes>{

        return api.trackItem(
            token = "Bearer $token",
            trackingId = trackingId

        )
    }


    suspend fun getUserCart(): Response<CartItemRes>{

        return api.getUserCart(
            token = "Bearer $token"
        )

    }

    suspend fun analyzeWaste(
        image:MultipartBody.Part?
    ):Response<AiSuggestionResponse>{

        return api.analyzeWaste(
            token = "Bearer $token",
            image = image
        )
    }

    suspend fun userAddItems() : Response<AddItemRes>{
        return  api.getUserReqItems(
            token = "Bearer $token"
        )
    }




}