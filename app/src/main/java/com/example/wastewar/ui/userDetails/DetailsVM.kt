package com.example.wastewar.ui.userDetails

import android.util.Log
import androidx.compose.ui.text.MultiParagraph
import androidx.lifecycle.ViewModel
import okhttp3.MultipartBody

import androidx.lifecycle.viewModelScope
import com.example.wastewar.ui.auth.AuthState
import com.example.wastewar.ui.user.CouponData
import com.example.wastewar.ui.user.CouponRes
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

sealed class UserStatsState{
    data object Idle: UserStatsState();
    data object Loading: UserStatsState();
    data class Success(var data: UserStats): UserStatsState();
    data class Error(val message:String): UserStatsState()
}

sealed class CouponState{
    data object Idle: CouponState();
    data object Loading: CouponState();
    data class Success(var data: CouponRes): CouponState();
    data class Error(val message:String): CouponState()
}

sealed class GetCouponState{
    data object Idle: GetCouponState();
    data object Loading: GetCouponState();
    data class Success(var data: List<CouponData>): GetCouponState();
    data class Error(val message:String): GetCouponState()
}
class DetailsVM(
    private val repo: DetailsRepo
) : ViewModel() {

    private val _userDetailState = MutableStateFlow<UserDetailState>(UserDetailState.Idle)
    val userDetailState: StateFlow<UserDetailState> = _userDetailState.asStateFlow()

    private val _getProfileState = MutableStateFlow<GetProfileState>(GetProfileState.Idle)
    val getProfileState: StateFlow<GetProfileState> = _getProfileState.asStateFlow()

    private val _userStatsState = MutableStateFlow<UserStatsState>(UserStatsState.Idle)
    val userStatsState: StateFlow<UserStatsState> = _userStatsState.asStateFlow()

    private val _couponState = MutableStateFlow<CouponState>(CouponState.Idle)
    val couponState: StateFlow<CouponState> = _couponState.asStateFlow()

    private val _getCouponState = MutableStateFlow<GetCouponState>(GetCouponState.Idle)
    val getCouponState: StateFlow<GetCouponState> = _getCouponState.asStateFlow()



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

                Log.d("TAG", "userStats: ${response.code()}")
                Log.d("TAG", "userStats: ${response.body()}")
                Log.d("TAG", "userStats: ${response.isSuccessful}")
                Log.d("TAG", "userStats: ${response.message()}")

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

    fun userStats(){

        viewModelScope.launch{
            _userStatsState.value=UserStatsState.Loading

            try{
                val response=repo.get_user_stats()


                Log.d("TAG", "userStats: ${response.code()}")
                Log.d("TAG", "userStats: ${response.body()}")
                Log.d("TAG", "userStats: ${response.isSuccessful}")



                if(response.body()!=null && response.isSuccessful){



                    _userStatsState.value=UserStatsState.Success(response.body()!!)
                }
                else {
                    _userStatsState.value = UserStatsState.Error(response.message())
                }
            }
            catch (e:Exception){
                _userStatsState.value=UserStatsState.Error(e.message ?: "Something went wrong")
            }
        }


    }


    fun create_coupon(){

        viewModelScope.launch{
            _couponState.value=CouponState.Loading

            try{
                val response=repo.createUserCoupon()


                Log.d("TAG", "coupon: ${response.code()}")
                Log.d("TAG", "coupon: ${response.body()}")
                Log.d("TAG", "coupon: ${response.message()}")
                Log.d("TAG", "coupon: ${response.isSuccessful}")

                if(response.body()!=null && response.isSuccessful){
                    _couponState.value=CouponState.Success(response.body()!!)
                }
                else {
                    _couponState.value = CouponState.Error(response.message())
                }
            }
            catch (e:Exception){
                _couponState.value=CouponState.Error(e.message ?: "Something went wrong")
                }
        }
    }


    fun get_all_coupons(){
        viewModelScope.launch{
            _getCouponState.value=GetCouponState.Loading
            try{
                val response=repo.getAllCoupons()
                Log.d("TAG", "userStats: ${response.code()}")
                Log.d("TAG", "userStats: ${response.body()}")
                Log.d("TAG", "userStats: ${response.isSuccessful}")
                if(response.body()!=null && response.isSuccessful){
                    _getCouponState.value=GetCouponState.Success(response.body()!!)
                }

                else{
                    _getCouponState.value=GetCouponState.Error(response.message())
                }
            }

            catch (e:Exception){
                _getCouponState.value=GetCouponState.Error(e.message ?: "Something went wrong")
            }
    }




    }
}



