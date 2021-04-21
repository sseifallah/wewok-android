package fr.mi.wewok.data.remote_servise

import fr.mi.wewok.data.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WewokServise {

    @GET("wp-json/custom-api/bases")
    suspend fun getBases() : Response<List<BasesRawModel>>

    @GET("wp-json/custom-api/retier-ingediant")
    suspend fun getRetirerIngredients() : Response<List<RetirerIngredientRawModel>>

    @GET("wp-json/custom-api/menu_restaurent")
    suspend fun getToppings() : Response<ToppingsRawModel>

    @GET("wp-json/custom-api/menu_restaurent")
    suspend fun getProteines() : Response<ProteinesRawModel>

    @GET("wp-json/custom-api/menu_restaurent")
    suspend fun getFromages() : Response<FromagesRawModel>

    @GET("wp-json/custom-api/menu_restaurent")
    suspend fun getAutres() : Response<AutresRawModel>

    @GET("wp-json/custom-api/menu_restaurent_v2")
    suspend fun getWoks() : Response<WoksRawModel>

    @GET("wp-json/custom-api/menu_restaurent")
    suspend fun getBoissons() : Response<BoissonsRawModel>

    @GET("wp-json/custom-api/menu_restaurent")
    suspend fun getDesserts() : Response<DessertsRawModel>

    @GET("wp-json/custom-api/check_zone?")
    suspend fun checkZone(@Query("zip_code") zip_code:String) : Response<ZoneApiModel>


}