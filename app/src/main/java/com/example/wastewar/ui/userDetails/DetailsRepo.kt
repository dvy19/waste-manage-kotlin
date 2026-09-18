package com.example.wastewar.ui.userDetails

import com.example.wastewar.ui.ApiClient
import com.example.wastewar.ui.auth.SessionManager
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import retrofit2.http.Multipart

class DetailsRepo(
    private val sessionManager: SessionManager,
) {

    val api= ApiClient.profileApi


    fun String.toPlainText(): RequestBody {
        return this.toRequestBody("text/plain".toMediaType())
    }

    suspend fun create_profile(
        phoneNumber:String,
        city:String,
        pinCode:String,
        address: String,
        coordinates: List<Double>,
        profile: MultipartBody.Part?

    ) : Response<UserDetailsRes>{

        return api.createProfile(
            token = "Bearer ${sessionManager.getAccessToken()}",
            phoneNumber = phoneNumber.toPlainText(),
            city = city.toPlainText(),
            pinCode = pinCode.toPlainText(),
            address = address.toPlainText(),
            coordinates = coordinates.toString().toPlainText(),
            profile = profile
        )

    }
}