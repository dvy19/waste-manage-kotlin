package com.example.wastewar.ui.auth

import android.app.Application
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

sealed class AuthState{
    data object Loading:AuthState()
    data class Error(val message:String):AuthState()
    data class Success(val data: RegisterRes):AuthState()
    data object Idle:AuthState()
}


class AuthVM(application: Application): AndroidViewModel(application) {

    val repo=AuthRepo()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val registerState: StateFlow<AuthState> = _authState.asStateFlow()

    val loginState: StateFlow<AuthState> = _authState.asStateFlow()

    fun register_user(req:RegisterReq){

        viewModelScope.launch{

            _authState.value=AuthState.Loading

            try {
                val response=repo.registerUser(req)

                if(response.body()!=null && response.isSuccessful){
                    _authState.value=AuthState.Success(response.body()!!)
                }else{
                    _authState.value=AuthState.Error(response.message())
                }
            }catch (e:Exception){
                _authState.value=AuthState.Error(e.message ?: "Something went wrong")
                }
            }

        }

    fun login_user(req:LoginReq){
        viewModelScope.launch{
            _authState.value=AuthState.Loading
            try {
                val response=repo.loginUser(req)

                if(response.body()!=null && response.isSuccessful){
                    _authState.value=AuthState.Success(response.body()!!)

                    }else{
                    _authState.value=AuthState.Error(response.message())
                }
            }catch (e:Exception){
                _authState.value=AuthState.Error(e.message ?: "Something went wrong")

            }
        }
    }

}





