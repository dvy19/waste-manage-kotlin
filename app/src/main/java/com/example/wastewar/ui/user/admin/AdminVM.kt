package com.example.wastewar.ui.user.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class GetSalesItemState{
    data object Idle: GetSalesItemState();
    data object Loading: GetSalesItemState();
    data class Success(var data: SalesItemRes): GetSalesItemState();
    data class Error(val message:String): GetSalesItemState()
}

sealed class GetCentreState{
    data object Idle: GetCentreState();
    data object Loading: GetCentreState();
    data class Success(var data: CentreRes): GetCentreState();
    data class Error(val message:String): GetCentreState()
}
class AdminVM(
    private val repo:AdminRepo
):ViewModel() {

    private val _getSalesItemState = MutableStateFlow<GetSalesItemState>(GetSalesItemState.Idle)
    val getSalesItemState: StateFlow<GetSalesItemState> = _getSalesItemState.asStateFlow()

    private val _getCentreState = MutableStateFlow<GetCentreState>(GetCentreState.Idle)
    val getCentreState: StateFlow<GetCentreState> = _getCentreState.asStateFlow()

    fun get_sales_item(){

        viewModelScope.launch{
            _getSalesItemState.value=GetSalesItemState.Loading

            try{
                val response=repo.get_sales_item()
                if(response.body()!=null && response.isSuccessful) {
                    _getSalesItemState.value = GetSalesItemState.Success(response.body()!!)
                }
                else{
                    _getSalesItemState.value=GetSalesItemState.Error(response.message())
                }
            }
            catch (e:Exception){
                _getSalesItemState.value=GetSalesItemState.Error(e.message ?: "Something went wrong")
            }
            }


            }

    fun get_centres(){
        viewModelScope.launch{
            _getCentreState.value=GetCentreState.Loading

            try{
                val response=repo.get_centres()

                if(response.body()!=null && response.isSuccessful){
                    _getCentreState.value=GetCentreState.Success(response.body()!!)
                }
                else{
                    _getCentreState.value=GetCentreState.Error(response.message())
                    }
            }
            catch (e:Exception){
                _getCentreState.value=GetCentreState.Error(e.message ?: "Something went wrong")
            }


        }

    }

}






