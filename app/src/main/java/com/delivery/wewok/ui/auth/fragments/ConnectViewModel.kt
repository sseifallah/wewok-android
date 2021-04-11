package com.delivery.wewok.ui.auth.fragments

import android.util.Log
import androidx.lifecycle.*
import com.delivery.wewok.data.model.*
import com.delivery.wewok.domain.AuthRepository
import com.delivery.wewok.ui.details_wok.step1.DetailsWokActivityStep1ViewModel
import com.delivery.wewok.utils.*
import com.mobilemovement.kotlintvmaze.base.Resource
import com.mobilemovement.kotlintvmaze.base.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import io.paperdb.Paper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectViewModel @Inject constructor(
        private val savedStateHandle: SavedStateHandle,
        private val authRepository: AuthRepository): ViewModel(){

    var connectResponse : Resource<ConnectModelR>? = null
    val connectLiveData= SingleLiveEvent<Resource<ConnectModelR>>()

    var registerResponse : Resource<RegisterModelR>? = null
    val registerLiveData= SingleLiveEvent<Resource<RegisterModelR>>()

    var resetResponse : Resource<ResetPasswordModel>? = null
    val resetLiveData= SingleLiveEvent<Resource<ResetPasswordModel>>()

    var validateResponse : Resource<ResetPasswordModel>? = null
    val validateLiveData= SingleLiveEvent<Resource<ResetPasswordModel>>()

    var updateResponse : Resource<ResetPasswordModel>? = null
    val updateLiveData= SingleLiveEvent<Resource<ResetPasswordModel>>()

    var userInfosResponse : Resource<UserApiModel>? = null
    val userInfosLiveData= SingleLiveEvent<Resource<UserApiModel>>()

    var updateUserResponse : Resource<UpdateUserResponse>? = null
    val updateUserLiveData= SingleLiveEvent<Resource<UpdateUserResponse>>()


    fun connect(email : String, password : String) {
        viewModelScope.launch(Dispatchers.IO) {
           // _connectLiveData.postValue(Resource.loading())

            connectResponse  = authRepository.connect(email,password)
            Log.i("CONNECT_RESULT","$connectResponse")
            connectLiveData.postValue(connectResponse)
            if (connectResponse?.status?.equals(Status.SUCCESS)!!){
                Paper.book().write<Int>(IID,connectResponse?.data?.user?.id)
                Paper.book().write(USERNAME,connectResponse?.data?.user?.username)
                Paper.book().write(USER_EMAIL,connectResponse?.data?.user?.email)
                Paper.book().write(FIRST_NAME,connectResponse?.data?.user?.firstname)
                Paper.book().write(LAST_NAME,connectResponse?.data?.user?.lastname)
                Paper.book().write<Boolean>(CONNECTED,true)
            }
        }
    }

    fun signup(nom : String, prenom : String, email : String, tel : String, password : String,adresse : String) {
        viewModelScope.launch(Dispatchers.IO) {
            // _connectLiveData.postValue(Resource.loading())

            registerResponse = authRepository.register(nom , prenom , email , tel , password,adresse)
            Log.i("CONNECT_RESULT","$registerResponse")
            registerLiveData.postValue(registerResponse)

        }
    }

    fun resetPassword(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            resetResponse = authRepository.resetPassword(email)
            Log.i("RESET_RESULT R","$resetResponse")
            resetLiveData.postValue(resetResponse)
        }
    }

    fun validateCode(email: String,code : String) {
        viewModelScope.launch(Dispatchers.IO) {
            validateResponse = authRepository.validateCode(email,code)
            Log.i("VALIDATE_REQ R","$validateResponse")
            validateLiveData.postValue(validateResponse)
        }
    }

    fun updatePassword(email: String,code : String,password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            updateResponse = authRepository.updatePassword(email,code,password)
            Log.i("UPDATE_REQ R","$validateResponse")
            updateLiveData.postValue(updateResponse)
        }
    }

    fun userInfos(id : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            userInfosResponse = authRepository.getUserInfos(id)
            Log.i("Infos_REQ R","$userInfosResponse")
            userInfosLiveData.postValue(userInfosResponse)
            if (userInfosResponse?.status?.equals(Status.SUCCESS)!!) {
                Paper.book().write(FIRST_NAME, userInfosResponse?.data?.data?.first_name)
                Paper.book().write(LAST_NAME, userInfosResponse?.data?.data?.last_name)
                Paper.book().write(USER_ADR, userInfosResponse?.data?.data?.adresse)
                Paper.book().write(USER_NUM, userInfosResponse?.data?.data?.client_phone)
                Paper.book().write(USER_VILLE, userInfosResponse?.data?.data?.ville)
                Paper.book().write(IID,userInfosResponse?.data?.data?.client_id)
                Paper.book().write(USER_EMAIL, userInfosResponse?.data?.data?.client_email)
                Paper.book().write(CODE_ZONE, userInfosResponse?.data?.data?.postal_code)


            }
        }
    }

    fun updateUser(id : Int,firstName : String ,  lastName : String,  email : String
                   ,  telephone : String,  adresse : String, ville : String,code:String){
        viewModelScope.launch(Dispatchers.IO) {
            updateUserResponse = authRepository.updateUser(id ,firstName,  lastName,  email,  telephone,  adresse, ville,code)
            Log.i("UPDATE_REQ R","$validateResponse")
            updateUserLiveData.postValue(updateUserResponse)
        }
    }

}