package fr.mi.wewok.domain

import android.util.Log
import fr.mi.wewok.base.CODES_HTTP
import fr.mi.wewok.base.ErrorFactory
import fr.mi.wewok.base.InternetConnectionChecker
import fr.mi.wewok.base.ext.safeToString
import fr.mi.wewok.data.mapper.*
import fr.mi.wewok.data.model.*
import fr.mi.wewok.data.remote_servise.WewokServise
import com.mobilemovement.kotlintvmaze.base.Resource
import fr.mi.wewok.data.remote_servise.NotificationsService
import java.lang.Exception
import javax.inject.Inject

class NotificationsRepositoryImpl @Inject constructor(
        private val notificationsServise: NotificationsService,
        private val errorFactory: ErrorFactory,
        private val internetConnectionChecker: InternetConnectionChecker,
        private val zoneDomainMapper: ZoneDomainMapper
) : NotificationsRepository {

    override suspend fun setToken(client_id: Int, token: String): Resource<Any> {
        return if(internetConnectionChecker.check())
        {
            try {
                val resSetToken = notificationsServise.setToken(TokenRequest(client_id,token,"ANDROID"))
                when (resSetToken.code()){
                    CODES_HTTP.SUCCES.code -> {
                        val setToken = resSetToken.body()!!
                        Log.i("SET_TOKEN"," SUCCESS $setToken")
                        Resource.success(setToken)
                    }
                    else -> {
                        Log.i("SET_TOKEN"," ELSE $resSetToken")
                        Resource.error(errorFactory.createApiCodeMessage(resSetToken.code()))
                    }
                }
            }catch (ex:Exception)
            {
                Log.i("SET_TOKEN"," EXCEPTION $ex")
                Resource.error<Any>(errorFactory.createApiErrorMessage(ex))
            }
        }else
        {
            Log.i("SET_TOKEN","ELSE2 ")
            Resource.noInternet(errorFactory.createNoInternetConnectionMessage())
        }
    }

    override suspend fun sendNotification(client_id: Int, title: String, message: String): Resource<Any> {
        return if(internetConnectionChecker.check())
        {
            try {
                val resNotif = notificationsServise.sendNotification(SendNotificationRequest(client_id,title,message))
                when (resNotif.code()){
                    CODES_HTTP.SUCCES.code -> {
                        val notif = resNotif.body()!!
                        Log.i("SET_TOKEN_NOTIF"," SUCCESS $notif")
                        Resource.success(notif)
                    }
                    else -> {
                        Log.i("SET_TOKEN_NOTIF"," ELSE $resNotif")
                        Resource.error(errorFactory.createApiCodeMessage(resNotif.code()))
                    }
                }
            }catch (ex:Exception)
            {
                Log.i("SET_TOKEN_NOTIF"," EXCEPTION $ex")
                Resource.error<Any>(errorFactory.createApiErrorMessage(ex))
            }
        }else
        {
            Log.i("SET_TOKEN_NOTIF","ELSE2 ")
            Resource.noInternet(errorFactory.createNoInternetConnectionMessage())
        }
    }

}