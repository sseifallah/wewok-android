package fr.mi.wewok.domain

import android.util.Log
import fr.mi.wewok.base.CODES_HTTP
import fr.mi.wewok.base.ErrorFactory
import fr.mi.wewok.base.InternetConnectionChecker
import fr.mi.wewok.data.mapper.ConnectDomainMapper
import fr.mi.wewok.data.mapper.RegisterDomainMapper
import fr.mi.wewok.data.mapper.ResetPasswordDomainMapper
import fr.mi.wewok.data.model.*
import fr.mi.wewok.data.remote_servise.AuthService
import com.mobilemovement.kotlintvmaze.base.Resource
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
