package fr.mi.wewok.data.remote_servise

import fr.mi.wewok.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface AuthService {

    @POST("cutom-api/auth/generate_auth_cookie/?")
    suspend fun connecter(@Query("username") username:String, @Query("password") password:String) : Response<ConnectModel>

    @POST("wp-json/custom-api/register_client")
    suspend fun register(@Body jsonObject: RegisterRequest) : Response<RegisterModel>

    @POST("wp-json/bdpwr/v1/reset-password/?")
    suspend fun resetPassword(@Query("email") email:String) : Response<ResetPasswordApiModel>

    @POST("wp-json/bdpwr/v1/validate-code?")
    suspend fun validateCode(@Query("email") email:String,@Query("code") code:String) : Response<ResetPasswordApiModel>

    @POST("wp-json/bdpwr/v1/set-password?")
    suspend fun updatePassword(@Query("email") email:String,@Query("code") code:String,@Query("password") password:String) : Response<ResetPasswordApiModel>

    @GET("wp-json/custom-api/user_get?")
    suspend fun getUserInfos(@Query("user_id") client_id:Int) : Response<UserApiModel>

    @POST("wp-json/custom-api/user_update")
    suspend fun updateUser(@Body jsonObject: UpdateUserRequest) : Response<UpdateUserResponse>

}