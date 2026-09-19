package com.example.wastewar.ui.user.admin

import com.example.wastewar.ui.ApiClient
import com.example.wastewar.ui.auth.SessionManager
import retrofit2.Response

class AdminRepo(
    private val sessionManager: SessionManager
) {


    val api= ApiClient.adminApi

    val token=sessionManager.getAccessToken()

    suspend fun get_sales_item():Response<SalesItemRes>{

        return api.getSaleItem()

    }

    suspend fun get_centres():Response<CentreRes>{
        return api.getCentres()
    }

    suspend fun single_sale_item_details(id: String) : Response<SingleSaleItem>{
        return api.saleItemDetails(
            token = "Bearer ${token}",
            id = id
        )
    }
}