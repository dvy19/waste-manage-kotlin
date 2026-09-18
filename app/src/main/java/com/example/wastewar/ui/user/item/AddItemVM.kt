package com.example.wastewar.ui.user.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

class AddItemVM(
    private val repo: AddItemRepo
) : ViewModel() {

    private val _addItemState = MutableStateFlow<AddItemState>(AddItemState.Idle)
    val addItemState: StateFlow<AddItemState> = _addItemState.asStateFlow()

    private val _trackItemState = MutableStateFlow<TrackItemState>(TrackItemState.Idle)
    val trackItemState: StateFlow<TrackItemState> = _trackItemState.asStateFlow()


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


}







