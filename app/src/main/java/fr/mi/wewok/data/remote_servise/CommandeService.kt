package fr.mi.wewok.data.remote_servise

import fr.mi.wewok.data.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CommandeService {

    @POST("wp-json/custom-api/save_order_v2")
    suspend fun saveOrder(@Body jsonObject: SaveRequest) : Response<SaveResponse>

    @POST("wp-json/custom-api/get_paiement_url")
    suspend fun getPaymentUrl(@Body jsonObject: GetUrlRequest) : Response<GetPaymentUrl>

    @POST("wp-json/custom-api/register_client")
    suspend fun getCommandes(@Body jsonObject: RegisterRequest) : Response<WoksRawModel>

    @POST("wp-json/custom-api/set_order_status")
    suspend fun updatesStatus(@Body jsonObject: UpdateStatusRequest) : Response<UpdateResponse>


}