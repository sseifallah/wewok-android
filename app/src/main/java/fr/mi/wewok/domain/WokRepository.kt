package fr.mi.wewok.domain

import fr.mi.wewok.data.model.*
import com.mobilemovement.kotlintvmaze.base.Resource

interface WokRepository {
    suspend fun getBases() : Resource<List<BasesModel>>

    suspend fun getRetirerIngredients() : Resource<List<RetirerIngredientModel>>

    suspend fun getToppings() : Resource<List<ToppingsModel>>

    suspend fun getProteines() : Resource<List<ProteinesModel>>

    suspend fun getFromages() : Resource<List<FromagesModel>>

    suspend fun getAutres() : Resource<List<AutresModel>>

    suspend fun getWoks() : Resource<WoksRawModel>

    suspend fun getBoissons() : Resource<List<BoissonsModel>>

    suspend fun getDesserts() : Resource<List<DessertsModel>>

    suspend fun checkZone(zoneCode : String) : Resource<ZoneModel>
}