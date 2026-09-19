package com.example.wastewar.ui.user.admin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class GetSalesItemState{
    data object Idle: GetSalesItemState();
    data object Loading: GetSalesItemState();
    data class Success(val data: SalesItemRes): GetSalesItemState();
    data class Error(val message:String): GetSalesItemState()
}

sealed class GetCentreState{
    data object Idle: GetCentreState();
    data object Loading: GetCentreState();
    data class Success(var data: CentreRes): GetCentreState();
    data class Error(val message:String): GetCentreState()
}

sealed class SingleSaleItemState{
    data object Idle: SingleSaleItemState();
    data object Loading: SingleSaleItemState();
    data class Success(var data: SingleSaleItem): SingleSaleItemState();
    data class Error(val message:String): SingleSaleItemState()
}
class AdminVM(
    private val repo:AdminRepo
):ViewModel() {

    private val _getSalesItemState = MutableStateFlow<GetSalesItemState>(GetSalesItemState.Idle)
    val getSalesItemState: StateFlow<GetSalesItemState> = _getSalesItemState.asStateFlow()

    private val _getCentreState = MutableStateFlow<GetCentreState>(GetCentreState.Idle)
    val getCentreState: StateFlow<GetCentreState> = _getCentreState.asStateFlow()

    private val _singleSaleItemState = MutableStateFlow<SingleSaleItemState>(SingleSaleItemState.Idle)
    val singleSaleItemState: StateFlow<SingleSaleItemState> = _singleSaleItemState.asStateFlow()

    fun get_sales_item() {

        viewModelScope.launch {
            _getSalesItemState.value = GetSalesItemState.Loading

            try {
                val response = repo.get_sales_item()

                Log.d("SALE_ITEM", "code = ${response.code()}")
                Log.d("SALE_ITEM", "body = ${response.body()}")
                Log.d("SALE_ITEM", "error = ${response.errorBody()?.string()}")
                Log.d("TAG", "get_sales_item: ${response.body()}")

                if (response.body() != null && response.isSuccessful) {
                    _getSalesItemState.value = GetSalesItemState.Success(response.body()!!)
                } else {
                    _getSalesItemState.value = GetSalesItemState.Error(response.message())
                }
            } catch (e: Exception) {
                _getSalesItemState.value =
                    GetSalesItemState.Error(e.message ?: "Something went wrong")
            }
        }


    }


    fun get_centres() {
        viewModelScope.launch {
            _getCentreState.value = GetCentreState.Loading

            try {
                val response = repo.get_centres()

                Log.d("TAG", "get_centres: ${response.body()}")

                if (response.body() != null && response.isSuccessful) {
                    _getCentreState.value = GetCentreState.Success(response.body()!!)
                } else {
                    _getCentreState.value = GetCentreState.Error(response.message())
                }
            } catch (e: Exception) {
                _getCentreState.value = GetCentreState.Error(e.message ?: "Something went wrong")
            }


        }

    }

    fun get_single_sale_item(id:String){

        viewModelScope.launch {
            _singleSaleItemState.value = SingleSaleItemState.Loading

            try {
                val response = repo.single_sale_item_details(id)

                Log.d("TAG", "get_single_sale_item: ${response.code()}")
                Log.d("TAG", "get_single_sale_item: ${response.errorBody()?.string()}")
                Log.d("TAG", "get_single_sale_item: ${response.body()}")


                if (response.body() != null && response.isSuccessful) {
                    _singleSaleItemState.value = SingleSaleItemState.Success(response.body()!!)
                    Log.d("TAG", "get_single_sale_item: ${response.body()}")
                } else {
                    _singleSaleItemState.value = SingleSaleItemState.Error(response.message())
                }
            } catch (e: Exception) {
                _singleSaleItemState.value =
                    SingleSaleItemState.Error(e.message ?: "Something went wrong")
            }
            }
        }


}












