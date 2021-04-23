package fr.mi.wewok.domain

import com.mobilemovement.kotlintvmaze.base.Resource
import fr.mi.wewok.data.model.BasesModel
import fr.mi.wewok.data.model.RetirerIngredientModel

interface NotificationsRepository{
    suspend fun setToken(client_id: Int, token: String) : Resource<Any>

    suspend fun sendNotification(client_id: Int, title: String, message: String) : Resource<Any>
}