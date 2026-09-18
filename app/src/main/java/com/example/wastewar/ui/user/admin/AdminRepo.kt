package com.example.wastewar.ui.user.admin

import com.example.wastewar.ui.ApiClient
import com.example.wastewar.ui.auth.SessionManager
import retrofit2.Response

class AdminRepo(
    private val sessionManager: SessionManager
) {


    val api= ApiClient.adminApi

    suspend fun get_sales_item():Response<SalesItemRes>{

        return api.getSaleItem()

    }

    suspend fun get_centres():Response<CentreRes>{
        return api.getCentres()
    }
}