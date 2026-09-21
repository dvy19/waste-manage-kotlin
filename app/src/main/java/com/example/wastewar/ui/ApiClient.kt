package com.example.wastewar.ui


import com.example.wastewar.ui.auth.RegisterInterface
import com.example.wastewar.ui.user.admin.AdminApiInterface
import com.example.wastewar.ui.user.item.ItemApiInterface
import com.example.wastewar.ui.userDetails.UserApiInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiClient {

    private const val BASE_URL = "https://waste-management-hiay.onrender.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    val registerApi: RegisterInterface by lazy {
        retrofit.create(RegisterInterface::class.java)
    }




    val profileApi: UserApiInterface by lazy {
        retrofit.create(UserApiInterface::class.java)
    }

    val addItemApi:ItemApiInterface by lazy {
        retrofit.create(ItemApiInterface::class.java)
    }

    val adminApi:AdminApiInterface by lazy {
        retrofit.create(AdminApiInterface::class.java)
    }


}

