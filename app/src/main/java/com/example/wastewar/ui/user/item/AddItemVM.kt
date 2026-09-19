package com.example.wastewar.ui.user.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wastewar.ui.user.AiSuggestionResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Response


sealed class AddItemState{
    object Idle:AddItemState()
    object Loading:AddItemState()
    data class Success(val data:AddItemRes):AddItemState()
    data class Error(val message:String):AddItemState()
}

sealed class TrackItemState{
    object Idle:TrackItemState()
    object Loading:TrackItemState()
    data class Success(val data:AddItemRes):TrackItemState()
    data class Error(val message:String):TrackItemState()
}
sealed class GetCartItemState{
    object Idle:GetCartItemState()
    object Loading:GetCartItemState()
    data class Success(val data:CartItemRes):GetCartItemState()
    data class Error(val message:String):GetCartItemState()
}

sealed class AnalyzeWasteState{
    object Idle:AnalyzeWasteState()
    object Loading:AnalyzeWasteState()
    data class Success(val data:AiSuggestionResponse):AnalyzeWasteState()
    data class Error(val message:String):AnalyzeWasteState()
}

sealed class GetUserReqItemsState{
    object Idle:GetUserReqItemsState()
    object Loading:GetUserReqItemsState()
    data class Success(val data:AddItemRes):GetUserReqItemsState()
    data class Error(val message:String):GetUserReqItemsState()
}


class AddItemVM(
    private val repo: AddItemRepo
) : ViewModel() {

    private val _addItemState = MutableStateFlow<AddItemState>(AddItemState.Idle)
    val addItemState: StateFlow<AddItemState> = _addItemState.asStateFlow()

    private val _trackItemState = MutableStateFlow<TrackItemState>(TrackItemState.Idle)
    val trackItemState: StateFlow<TrackItemState> = _trackItemState.asStateFlow()

    private val _getCartItemState = MutableStateFlow<GetCartItemState>(GetCartItemState.Idle)
    val getCartItemState: StateFlow<GetCartItemState> = _getCartItemState.asStateFlow()

    private val _analyzeWasteState = MutableStateFlow<AnalyzeWasteState>(AnalyzeWasteState.Idle)
    val analyzeWasteState: StateFlow<AnalyzeWasteState> = _analyzeWasteState.asStateFlow()

    private val _getUserReqItemsState = MutableStateFlow<GetUserReqItemsState>(GetUserReqItemsState.Idle)
    val getUserReqItemsState: StateFlow<GetUserReqItemsState> = _getUserReqItemsState.asStateFlow()

    fun add_item(
        name: String,
        quantity: String,
        weight: String,
        category: String,
        image: MultipartBody.Part?
    ) {

        viewModelScope.launch {

            _addItemState.value = AddItemState.Loading

            try {
                val response = repo.addItem(
                    name = name,
                    quantity = quantity,
                    weight = weight,
                    category = category,
                    image = image
                )

                if (response.isSuccessful && response.body() != null) {
                    _addItemState.value = AddItemState.Success(response.body()!!)
                } else {
                    _addItemState.value = AddItemState.Error(response.message())
                }

            } catch (e: Exception) {
                _addItemState.value = AddItemState.Error(e.message ?: "Unknown Error")
            }
        }
    }


    fun track_item(trackingId: String) {

        viewModelScope.launch {

            _trackItemState.value = TrackItemState.Loading

            try {
                val response = repo.trackItem(trackingId)

                if (response.isSuccessful && response.body() != null) {
                    _trackItemState.value = TrackItemState.Success(response.body()!!)
                }
                else {
                    _trackItemState.value = TrackItemState.Error(response.message())
                }

            }
            catch (e: Exception) {
                _trackItemState.value = TrackItemState.Error(e.message ?: "Unknown Error")
            }

        }

    }

    fun get_cart_items(){

        viewModelScope.launch {
            _getCartItemState.value=GetCartItemState.Loading

            try{
                val response=repo.getUserCart()


                if(response.isSuccessful && response.body()!=null){
                    _getCartItemState.value=GetCartItemState.Success(response.body()!!)
                }

                else{
                    _getCartItemState.value=GetCartItemState.Error(response.message())
                }
            }
            catch (e:Exception){
                _getCartItemState.value=GetCartItemState.Error(e.message ?: "Unknown Error")
            }
        }



    }

    fun analyze_waste(image:MultipartBody.Part?){

        viewModelScope.launch {

            _analyzeWasteState.value=AnalyzeWasteState.Loading

            try{
                val response=repo.analyzeWaste(image)


                if(response.isSuccessful && response.body()!=null){
                    _analyzeWasteState.value=AnalyzeWasteState.Success(response.body()!!)
                }

                else{
                    _analyzeWasteState.value=AnalyzeWasteState.Error(response.message())
                }
            }
            catch (e:Exception){
                _analyzeWasteState.value=AnalyzeWasteState.Error(e.message ?: "Unknown Error")
            }

        }



    }


    fun get_user_req_items(){
        viewModelScope.launch {
            _getUserReqItemsState.value=GetUserReqItemsState.Loading
            try{
                val response=repo.userAddItems()
                if(response.isSuccessful && response.body()!=null){
                    _getUserReqItemsState.value=GetUserReqItemsState.Success(response.body()!!)
                }

                else{
                    _getUserReqItemsState.value=GetUserReqItemsState.Error(response.message())
                }
            }

            catch (e:Exception){
                _getUserReqItemsState.value=GetUserReqItemsState.Error(e.message ?: "Unknown Error")
            }
        }

    }


}







