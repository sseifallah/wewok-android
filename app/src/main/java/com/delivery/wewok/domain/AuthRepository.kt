package com.delivery.wewok.domain

import com.delivery.wewok.data.model.*
import com.mobilemovement.kotlintvmaze.base.Resource

interface AuthRepository {
    suspend fun connect(username : String ,  password : String) : Resource<ConnectModelR>
    suspend fun register(nom : String ,  prenom : String,  email : String
                         ,  telephone : String,  password : String, adresse : String) : Resource<RegisterModelR>
    suspend fun resetPassword(email : String) : Resource<ResetPasswordModel>
    suspend fun validateCode(email : String,code : String) : Resource<ResetPasswordModel>
    suspend fun updatePassword(email : String,code : String, password: String) : Resource<ResetPasswordModel>
    suspend fun getUserInfos(id : Int) : Resource<UserApiModel>
    suspend fun updateUser(id : Int,firstName : String ,  lastName : String,  email : String
                           ,  telephone : String,  adresse : String, ville : String,code:String) : Resource<UpdateUserResponse>
}