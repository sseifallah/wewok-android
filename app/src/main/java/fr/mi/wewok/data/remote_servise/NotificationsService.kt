package fr.mi.wewok.data.remote_servise

import fr.mi.wewok.data.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface NotificationsService {

    @POST("wp-json/custom-api/set_push_token")
    suspend fun setToken(@Body jsonObject: TokenRequest) : Response<Any>

    @POST("wp-json/custom-api/send_push")
    suspend fun sendNotification(@Body jsonObject: SendNotificationRequest) : Response<Any>


}