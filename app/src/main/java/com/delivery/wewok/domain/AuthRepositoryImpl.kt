package com.delivery.wewok.domain

import android.util.Log
import com.delivery.wewok.base.CODES_HTTP
import com.delivery.wewok.base.ErrorFactory
import com.delivery.wewok.base.InternetConnectionChecker
import com.delivery.wewok.base.ext.safeToString
import com.delivery.wewok.data.mapper.ConnectDomainMapper
import com.delivery.wewok.data.mapper.RegisterDomainMapper
import com.delivery.wewok.data.mapper.ResetPasswordDomainMapper
import com.delivery.wewok.data.model.*
import com.delivery.wewok.data.remote_servise.AuthService
import com.delivery.wewok.data.remote_servise.WewokServise
import com.google.gson.JsonObject
import com.mobilemovement.kotlintvmaze.base.Resource
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.lang.Exception
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
        private val authService: AuthService,
        private val errorFactory: ErrorFactory,
        private val connectDomainMapper: ConnectDomainMapper,
        private val registerDomainMapper: RegisterDomainMapper,
        private val resetPasswordDomainMapper: ResetPasswordDomainMapper,
        private val internetConnectionChecker: InternetConnectionChecker
) : AuthRepository {
    override suspend fun connect(username : String ,  password : String): Resource<ConnectModelR> {
        return if(internetConnectionChecker.check())
        {
            try {
                var result = authService.connecter(username,password)
                if (result.code() == CODES_HTTP.SUCCES.code){
                    val connect = result.body()!!
                    if (connect.status.equals("error"))
                        Resource.error(connect.error)
                    else{
                        val mappedConnect = connectDomainMapper.invoke(connect)
                        Log.i("CONNECT_RESULT_REP","SUCCES")
                        Resource.success(mappedConnect)
                    }

                }
                else{
                    Log.i("CONNECT_RESULT_REP","ELSE")
                    Resource.error(result.message())
                }
            }catch (ex:Exception){
                Log.i("CONNECT_RESULT_REP","EXCEPTION")
                Resource.error<ConnectModelR>(errorFactory.createApiErrorMessage(ex))
            }
        }else
        {Log.i("CONNECT_RESULT_REP","NO_INTERNET")
            Resource.noInternet(errorFactory.createNoInternetConnectionMessage())
        }
    }

    override suspend fun register(nom: String, prenom: String, email: String, telephone: String, password: String, adreese : String): Resource<RegisterModelR> {
        return if(internetConnectionChecker.check())
        {
            try {
                val userInfo = RegisterRequest(  nom = nom,
                    prenom = prenom,
                    email = email,
                    telephone = telephone,
                    password = password,
                    adresse = adreese)
              /*  val nom = RequestBody.create("text/plain".toMediaTypeOrNull(), nom)
                val prenom = RequestBody.create("text/plain".toMediaTypeOrNull(), prenom)
                val email = RequestBody.create("text/plain".toMediaTypeOrNull(), email)
                val telephone = RequestBody.create("text/plain".toMediaTypeOrNull(), telephone)
                val password = RequestBody.create("text/plain".toMediaTypeOrNull(), password)
*/

                var result = authService.register(userInfo)
                Log.i("REGISTER_RESULT_REP","RES ${result}")
                if (result.code() == CODES_HTTP.SUCCES.code){
                    val register = result.body()!!
                    val mappedRegister = registerDomainMapper.invoke(register)
                    Log.i("REGISTER_RESULT_REP","SUCCES")
                    Resource.success(mappedRegister)

                }
                else{
                    Log.i("REGISTER_RESULT_REP","ELSE ${result.body()!!.message}")
                    Resource.error(errorFactory.createApiCodeMessage(result.code()))
                }
            }catch (ex:Exception){
                Log.i("REGISTER_RESULT_REP","EXCEPTION")
                Resource.error<RegisterModelR>(errorFactory.createApiErrorMessage(ex))
            }
        }else
        { Log.i("REGISTER_RESULT_REP","NO_INTERNET")
            Resource.noInternet(errorFactory.createNoInternetConnectionMessage())
        }
    }

    override suspend fun resetPassword(email: String): Resource<ResetPasswordModel> {
        return if(internetConnectionChecker.check())
        {
            try {

                var result = authService.resetPassword(email)
                if (result.code() == CODES_HTTP.SUCCES.code){
                    Log.i("RESET_RESULT", "true $result")
                    val check = result.body()!!
                    Log.i("RESET_RESULT", "true Body $check")
                    val mappedzone = resetPasswordDomainMapper.invoke(check)
                    Resource.success(mappedzone)
                }
                else{
                    Log.i("CHECK_RESULT","ELSE")
                    Resource.error(errorFactory.createApiCodeMessage(result.code()))
                }
            }catch (ex:Exception){
                Log.i("CHECK_RESULT_REP","EXCEPTION $ex")
                Resource.error<ResetPasswordModel>(errorFactory.createApiErrorMessage(ex))
            }
        }else
        { Log.i("CHECK_RESULT_REP","NO_INTERNET")
            Resource.noInternet(errorFactory.createNoInternetConnectionMessage())
        }
    }

    override suspend fun validateCode(email: String,code: String): Resource<ResetPasswordModel> {
        return if(internetConnectionChecker.check())
        {
            try {

                var result = authService.validateCode(email,code)
                if (result.code() == CODES_HTTP.SUCCES.code){
                    Log.i("VALIDATE_REQ", "true $result")
                    val check = result.body()!!
                    Log.i("VALIDATE_REQ", "true Body $check")
                    val mappedzone = resetPasswordDomainMapper.invoke(check)
                    Resource.success(mappedzone)
                }
                else{
                    Log.i("VALIDATE_REQ","ELSE")
                    Resource.error(errorFactory.createApiCodeMessage(result.code()))
                }
            }catch (ex:Exception){
                Log.i("VALIDATE_REQ","EXCEPTION $ex")
                Resource.error<ResetPasswordModel>(errorFactory.createApiErrorMessage(ex))
            }
        }else
        { Log.i("VALIDATE_REQ","NO_INTERNET")
            Resource.noInternet(errorFactory.createNoInternetConnectionMessage())
        }
    }

    override suspend fun updatePassword(
        email: String,
        code: String,
        password: String
    ): Resource<ResetPasswordModel> {
        return if(internetConnectionChecker.check())
        {
            try {

                var result = authService.updatePassword(email,code,password)
                if (result.code() == CODES_HTTP.SUCCES.code){
                    Log.i("UPDATE_REQ", "true $result")
                    val check = result.body()!!
                    Log.i("UPDATE_REQ", "true Body $check")
                    val mappedzone = resetPasswordDomainMapper.invoke(check)
                    Resource.success(mappedzone)
                }
                else{
                    Log.i("UPDATE_REQ","ELSE")
                    Resource.error(errorFactory.createApiCodeMessage(result.code()))
                }
            }catch (ex:Exception){
                Log.i("UPDATE_REQ","EXCEPTION $ex")
                Resource.error<ResetPasswordModel>(errorFactory.createApiErrorMessage(ex))
            }
        }else
        { Log.i("UPDATE_REQ","NO_INTERNET")
            Resource.noInternet(errorFactory.createNoInternetConnectionMessage())
        }
    }

    override suspend fun getUserInfos(id: Int): Resource<UserApiModel> {
        return if(internetConnectionChecker.check())
        {
            try {

                var result = authService.getUserInfos(id)
                if (result.code() == CODES_HTTP.SUCCES.code){
                    Log.i("INFOS_REQ", "true $result")
                    val userInfos = result.body()!!
                    Log.i("INFOS_REQ", "true Body $userInfos")
                    Resource.success(userInfos)
                }
                else{
                    Log.i("INFOS_REQ","ELSE")
                    Resource.error(errorFactory.createApiCodeMessage(result.code()))
                }
            }catch (ex:Exception){
                Log.i("INFOS_REQ","EXCEPTION $ex")
                Resource.error<UserApiModel>(errorFactory.createApiErrorMessage(ex))
            }
        }else
        { Log.i("INFOS_REQ","NO_INTERNET")
            Resource.noInternet(errorFactory.createNoInternetConnectionMessage())
        }
    }

    override suspend fun updateUser(id: Int, firstName: String, lastName: String, email: String, telephone: String, adresse: String, ville: String, code: String): Resource<UpdateUserResponse> {
        return if(internetConnectionChecker.check())
        {
            try {

                var result = authService.updateUser(UpdateUserRequest(id,firstName,lastName,email,telephone,adresse,ville,code))
                if (result.code() == CODES_HTTP.SUCCES.code){
                    Log.i("UPDATE_REQ", "true $result")
                    val userInfos = result.body()!!
                    Log.i("UPDATE_REQ", "true Body $userInfos")
                    Resource.success(userInfos)
                }
                else{
                    Log.i("UPDATE_REQ","ELSE")
                    Resource.error(errorFactory.createApiCodeMessage(result.code()))
                }
            }catch (ex:Exception){
                Log.i("UPDATE_REQ","EXCEPTION $ex")
                Resource.error<UpdateUserResponse>(errorFactory.createApiErrorMessage(ex))
            }
        }else
        { Log.i("UPDATE_REQ","NO_INTERNET")
            Resource.noInternet(errorFactory.createNoInternetConnectionMessage())
        }
    }

}
