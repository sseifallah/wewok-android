package com.delivery.wewok.domain

import android.util.Log
import com.delivery.wewok.base.CODES_HTTP
import com.delivery.wewok.base.ErrorFactory
import com.delivery.wewok.base.InternetConnectionChecker
import com.delivery.wewok.data.mapper.ConnectDomainMapper
import com.delivery.wewok.data.mapper.RegisterDomainMapper
import com.delivery.wewok.data.mapper.ResetPasswordDomainMapper
import com.delivery.wewok.data.model.*
import com.delivery.wewok.data.remote_servise.AuthService
import com.delivery.wewok.data.remote_servise.CommandeService
import com.mobilemovement.kotlintvmaze.base.Resource
import java.lang.Exception
import javax.inject.Inject

class CommandeRepositoryImpl  @Inject constructor(
        private val commandeService: CommandeService,
        private val errorFactory: ErrorFactory,
        private val connectDomainMapper: ConnectDomainMapper,
        private val registerDomainMapper: RegisterDomainMapper,
        private val resetPasswordDomainMapper: ResetPasswordDomainMapper,
        private val internetConnectionChecker: InternetConnectionChecker
) : CommandeRepository {
    override suspend fun saveOrder( client_adress: String, client_city: String, client_country: String, client_phone: String, client_postcode : String,client_state: String,
                                   userId: Int, total: String, menuIds: ArrayList<String>): Resource<SaveResponse> {
        return if(internetConnectionChecker.check())
        {
            try {

                val resBases = commandeService.saveOrder (SaveRequest(client_adress, client_city, client_country, client_phone,client_postcode, client_state,
                        userId,menuIds, total ))
                //(SaveRequest("lyon rue 1", "lyon", "FR", "12345678","68100", "lyon",
                //                        55,menuIds, total ))

                when (resBases.code()){
                    CODES_HTTP.SUCCES.code -> {
                        val bases = resBases.body()!!
                        Log.i("SAVE_RESP"," $bases")
                        Resource.success(bases)

                    }
                    else -> {
                        Log.i("SAVE_RESP"," else ${resBases.code()}")
                        Resource.error(errorFactory.createApiCodeMessage(resBases.code()))
                    }
                }
            }catch (ex: Exception)
            {    Log.i("SAVE_RESP"," exep $ex")
                Resource.error<SaveResponse>(errorFactory.createApiErrorMessage(ex))
            }
        }else
        {
            Log.i("SAVE_RESP"," No Internet")
            Resource.noInternet(errorFactory.createNoInternetConnectionMessage())
        }
    }

    override suspend fun getPaymentUrl(order_id: Int): Resource<GetPaymentUrl> {
        return if(internetConnectionChecker.check())
        {
            try {

                val resurl = commandeService.getPaymentUrl(GetUrlRequest(order_id ))
                when (resurl.code()){
                    CODES_HTTP.SUCCES.code -> {
                        val bases = resurl.body()!!
                        Log.i("URL_RESP"," $bases")
                        Resource.success(bases)

                    }
                    else -> {
                        Log.i("SAVURL_RESPE_RESP"," else ${resurl.code()}")
                        Resource.error(errorFactory.createApiCodeMessage(resurl.code()))
                    }
                }
            }catch (ex: Exception)
            {    Log.i("URL_RESP"," exep $ex")
                Resource.error<GetPaymentUrl>(errorFactory.createApiErrorMessage(ex))
            }
        }else
        {
            Log.i("URL_RESP"," No Internet")
            Resource.noInternet(errorFactory.createNoInternetConnectionMessage())
        }
    }

    override suspend fun updatestatus(order_id: Int, status: String): Resource<UpdateResponse> {
        return if(internetConnectionChecker.check())
        {
            try {

                val resurl = commandeService.updatesStatus(UpdateStatusRequest(order_id,status ))
                when (resurl.code()){
                    CODES_HTTP.SUCCES.code -> {
                        val bases = resurl.body()!!
                        Log.i("UPDATE_RESP"," $bases")
                        Resource.success(bases)

                    }
                    else -> {
                        Log.i("UPDATE_RESP"," else ${resurl.code()}")
                        Resource.error(errorFactory.createApiCodeMessage(resurl.code()))
                    }
                }
            }catch (ex: Exception)
            {    Log.i("UPDATE_RESP"," exep $ex")
                Resource.error<UpdateResponse>(errorFactory.createApiErrorMessage(ex))
            }
        }else
        {
            Log.i("UPDATE_RESP"," No Internet")
            Resource.noInternet(errorFactory.createNoInternetConnectionMessage())
        }
    }

    /*override suspend fun getCommandes(userId: String): Resource<WoksRawModel> {
    }*/
}