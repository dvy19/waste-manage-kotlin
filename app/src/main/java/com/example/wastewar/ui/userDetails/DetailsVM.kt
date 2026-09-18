package com.example.wastewar.ui.userDetails

import androidx.compose.ui.text.MultiParagraph
import androidx.lifecycle.ViewModel
import okhttp3.MultipartBody

import androidx.lifecycle.viewModelScope
import com.example.wastewar.ui.auth.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class UserDetailState{
    data object Idle: UserDetailState();
    data object Loading: UserDetailState();
    data class Success(var data: UserDetailsRes): UserDetailState();
    data class Error(val message:String): UserDetailState()
}

sealed class GetProfileState{
    data object Idle: GetProfileState();
    data object Loading: GetProfileState();
    data class Success(var data: ProfileRes): GetProfileState();
    data class Error(val message:String): GetProfileState()
}

class DetailsVM(
    private val repo: DetailsRepo
) : ViewModel() {

    private val _userDetailState = MutableStateFlow<UserDetailState>(UserDetailState.Idle)
    val userDetailState: StateFlow<UserDetailState> = _userDetailState.asStateFlow()

    private val _getProfileState = MutableStateFlow<GetProfileState>(GetProfileState.Idle)
    val getProfileState: StateFlow<GetProfileState> = _getProfileState.asStateFlow()


    fun createUserProfile(
        phoneNumber:String,
        city: String,
        pinCode: String,
        address:String,
        profile: MultipartBody.Part?,
        coordinates:List<Double>
        ) {

        viewModelScope.launch{
            _userDetailState.value=UserDetailState.Loading

            try{

                val response=repo.create_profile(
                    phoneNumber = phoneNumber,
                    city = city,
                    pinCode = pinCode,
                    address = address,
                    profile=profile,
                    coordinates = coordinates
                )

                if(response.body()!=null && response.isSuccessful){
                    _userDetailState.value=UserDetailState.Success(response.body()!!)
                }
                else{
                    _userDetailState.value=UserDetailState.Error(response.message())
                }
            }
            catch (e:Exception){
                _userDetailState.value=UserDetailState.Error(e.message ?: "Something went wrong")
            }




            }

        }


    fun get_profile(){



        viewModelScope.launch{
            _getProfileState.value=GetProfileState.Loading


            try{

                val response=repo.getUserProfile()

                if(response.body()!=null && response.isSuccessful){
                    _getProfileState.value=GetProfileState.Success(response.body()!!)
                }
                else{
                    _getProfileState.value=GetProfileState.Error(response.message())
                }
            }
            catch (e:Exception){
                _getProfileState.value=GetProfileState.Error(e.message ?: "Something went wrong")
                }
            }


        }


    }
