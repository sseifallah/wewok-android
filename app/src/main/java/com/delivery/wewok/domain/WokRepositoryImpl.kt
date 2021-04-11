package com.delivery.wewok.domain

import android.util.Log
import com.delivery.wewok.base.CODES_HTTP
import com.delivery.wewok.base.ErrorFactory
import com.delivery.wewok.base.InternetConnectionChecker
import com.delivery.wewok.base.ext.safeToString
import com.delivery.wewok.data.mapper.*
import com.delivery.wewok.data.model.*
import com.delivery.wewok.data.remote_servise.WewokServise
import com.mobilemovement.kotlintvmaze.base.Resource
import java.lang.Exception
import javax.inject.Inject

class WokRepositoryImpl @Inject constructor(
        private val wewokServise: WewokServise,
        private val basesDomainMapper : BasesDomainMapper,
        private val retirerIngredientMapper : RetirerIngredientMapper,
        private val toppingsDomainMapper : ToppingsDomainMapper,
        private val proteinesDomainMapper : ProteinesDomainMapper,
        private val fromagesDomainMapper : FromagesDomainMapper,
        private val autresDomainMapper : AutresDomainMapper,
        private val woksDomainMapper : WoksDomainMapper,
        private val boissonsDomainMapper:BoissonsDomainMapper,
        private val dessertsDomainMapper:DessertsDomainMapper,
        private val errorFactory: ErrorFactory,
        private val internetConnectionChecker: InternetConnectionChecker,
        private val zoneDomainMapper: ZoneDomainMapper
) : WokRepository {
    override suspend fun getBases(): Resource<List<BasesModel>> {
        return if(internetConnectionChecker.check())
        {
            try {
                val resBases = wewokServise.getBases()
                when (resBases.code()){
                    CODES_HTTP.SUCCES.code -> {
                        val bases = resBases.body()!!
                        val mappedBases = bases.map {  basesDomainMapper.invoke(it)}
                        Resource.success(mappedBases)
                    }
                    else -> {
                        Resource.error(errorFactory.createApiCodeMessage(resBases.code()))
                    }
                }
            }catch (ex:Exception)
            {
                Resource.error<List<BasesModel>>(errorFactory.createApiErrorMessage(ex))
            }
        }else
        {
            Resource.noInternet(errorFactory.createNoInternetConnectionMessage())
        }
    }

    override suspend fun getRetirerIngredients(): Resource<List<RetirerIngredientModel>> {
        return if(internetConnectionChecker.check())
        {
           try {
               val resRetirerIngredients = wewokServise.getRetirerIngredients()
               when (resRetirerIngredients.code()){
                   CODES_HTTP.SUCCES.code -> {
                       val retirerIngredients = resRetirerIngredients.body()!!
                       val mappedRetirerIngredients = retirerIngredients.map {  retirerIngredientMapper.invoke(it)}
                       Resource.success(mappedRetirerIngredients)
                   }
                   else -> {
                       Resource.error(errorFactory.createApiCodeMessage(resRetirerIngredients.code()))
                   }
               }
           }catch (ex:Exception){
               Resource.error<List<RetirerIngredientModel>>(errorFactory.createApiErrorMessage(ex))
           }
        }else
        {
            Resource.noInternet(errorFactory.createNoInternetConnectionMessage())
        }
    }

    override suspend fun getToppings(): Resource<List<ToppingsModel>> {
        return if(internetConnectionChecker.check())
        {
            try {
                val resToppings = wewokServise.getToppings()
                when (resToppings.code()){
                    CODES_HTTP.SUCCES.code -> {
                        val toppings = resToppings.body()!!
                        val mappedToppings = toppingsDomainMapper.invoke(toppings)
                        Resource.success(mappedToppings)
                    }
                    else -> {
                        Resource.error(errorFactory.createApiCodeMessage(resToppings.code()))
                    }
                }
            }catch (ex:Exception){
                Resource.error<List<ToppingsModel>>(errorFactory.createApiErrorMessage(ex))
            }
        }else
        {
            Resource.noInternet(errorFactory.createNoInternetConnectionMessage())
        }
    }

    override suspend fun getProteines(): Resource<List<ProteinesModel>> {
        return if(internetConnectionChecker.check())
        {
            try {
                val resProteines = wewokServise.getProteines()
                when (resProteines.code()){
                    CODES_HTTP.SUCCES.code -> {
                        val proteines = resProteines.body()!!
                        val mappedProteines = proteinesDomainMapper.invoke(proteines)
                        Resource.success(mappedProteines)
                    }
                    else -> {
                        Resource.error(errorFactory.createApiCodeMessage(resProteines.code()))
                    }
                }
            }catch (ex:Exception){
                Resource.error<List<ProteinesModel>>(errorFactory.createApiErrorMessage(ex))
            }
        }else
        {
            Resource.noInternet(errorFactory.createNoInternetConnectionMessage())
        }
    }

    override suspend fun getFromages(): Resource<List<FromagesModel>> {
        return if(internetConnectionChecker.check())
        {
            try {
                val resFromages = wewokServise.getFromages()
                when (resFromages.code()){
                    CODES_HTTP.SUCCES.code -> {
                        val fromages = resFromages.body()!!
                        val mappedFromages = fromagesDomainMapper.invoke(fromages)
                        Resource.success(mappedFromages)
                    }
                    else -> {
                        Resource.error(errorFactory.createApiCodeMessage(resFromages.code()))
                    }
                }
            }catch (ex:Exception){
                Resource.error<List<FromagesModel>>(errorFactory.createApiErrorMessage(ex))
            }
        }else
        {
            Resource.noInternet(errorFactory.createNoInternetConnectionMessage())
        }
    }

    override suspend fun getAutres(): Resource<List<AutresModel>> {
        return if(internetConnectionChecker.check())
        {
            try {
                val resAutres = wewokServise.getAutres()
                when (resAutres.code()){
                    CODES_HTTP.SUCCES.code -> {
                        val autres = resAutres.body()!!
                        val mappedAutres = autresDomainMapper.invoke(autres)
                        Resource.success(mappedAutres)
                    }
                    else -> {
                        Resource.error(errorFactory.createApiCodeMessage(resAutres.code()))
                    }
                }
            }catch (ex:Exception){
                Resource.error<List<AutresModel>>(errorFactory.createApiErrorMessage(ex))
            }
        }else
        {
            Resource.noInternet(errorFactory.createNoInternetConnectionMessage())
        }
    }

    override suspend fun getWoks(): Resource<WoksRawModel> {
        return if(internetConnectionChecker.check())
        {
            try {
                val resAutres = wewokServise.getWoks()
                Log.i("WOK_RESP IMPL : "," ${resAutres} ")
                when (resAutres.code()){
                    CODES_HTTP.SUCCES.code -> {
                        val woks = resAutres.body()!!
                    //    val mappedWoks = woksDomainMapper.invoke(woks)
                        Resource.success(woks)
                    }
                    else -> {
                        Resource.error(errorFactory.createApiCodeMessage(resAutres.code()))
                    }
                }
            }catch (ex:Exception){
                Log.i("WOK_RESP IMPL : ","Exception ${ex.message} ")
                Resource.error<WoksRawModel>(errorFactory.createApiErrorMessage(ex))
            }
        }else
        {
            Log.i("WOK_RESP IMPL : "," else ")
            Resource.noInternet(errorFactory.createNoInternetConnectionMessage())
        }
    }

    override suspend fun getBoissons(): Resource<List<BoissonsModel>> {
        return if(internetConnectionChecker.check())
        {
            try {
                val resBoissons = wewokServise.getBoissons()
                when (resBoissons.code()){
                    CODES_HTTP.SUCCES.code -> {
                        val boissons = resBoissons.body()!!
                        val mappedBoissons = boissonsDomainMapper.invoke(boissons)
                        Resource.success(mappedBoissons)
                    }
                    else -> {
                        Resource.error(errorFactory.createApiCodeMessage(resBoissons.code()))
                    }
                }
            }catch (ex:Exception){
                Resource.error<List<BoissonsModel>>(errorFactory.createApiErrorMessage(ex))
            }
        }else
        {
            Resource.noInternet(errorFactory.createNoInternetConnectionMessage())
        }
    }

    override suspend fun getDesserts(): Resource<List<DessertsModel>> {
        return if(internetConnectionChecker.check())
        {
            try {
                val resDesserts = wewokServise.getDesserts()
                when (resDesserts.code()){
                    CODES_HTTP.SUCCES.code -> {
                        val desserts = resDesserts.body()!!
                        val mappedDesserts = dessertsDomainMapper.invoke(desserts)
                        Resource.success(mappedDesserts)
                    }
                    else -> {
                        Resource.error(errorFactory.createApiCodeMessage(resDesserts.code()))
                    }
                }
            }catch (ex:Exception){
                Resource.error<List<DessertsModel>>(errorFactory.createApiErrorMessage(ex))
            }
        }else
        {
            Resource.noInternet(errorFactory.createNoInternetConnectionMessage())
        }
    }

    override suspend fun checkZone(zoneCode: String): Resource<ZoneModel> {
        return if(internetConnectionChecker.check())
        {
            try {

                var result = wewokServise.checkZone(zoneCode)
                if (result.code() == CODES_HTTP.SUCCES.code){

                    val check = result.body()!!
                    Log.i("CHECK_RESULT","SUCCESS ${check}")
                    if (check.status == false) {
                        Log.i("CHECK_RESULT", "false")
                        Resource.error<ZoneModel>(check.error.safeToString())
                    }
                    else {
                        Log.i("CHECK_RESULT", "true")
                        val mappedzone = zoneDomainMapper.invoke(check)
                        Resource.success(mappedzone)
                    }

                }
                else{
                    Log.i("CHECK_RESULT","ELSE")
                    Resource.error(result.message())
                }
            }catch (ex:Exception){
                Log.i("CHECK_RESULT_REP","EXCEPTION $ex")
                Resource.error<ZoneModel>(errorFactory.createApiErrorMessage(ex))
            }
        }else
        { Log.i("CHECK_RESULT_REP","NO_INTERNET")
            Resource.noInternet(errorFactory.createNoInternetConnectionMessage())
        }
    }
}