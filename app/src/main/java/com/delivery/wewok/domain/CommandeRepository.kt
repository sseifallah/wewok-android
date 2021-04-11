package com.delivery.wewok.domain

import com.delivery.wewok.data.model.*
import com.mobilemovement.kotlintvmaze.base.Resource

interface CommandeRepository {

    suspend fun saveOrder(client_adress: String, client_city: String, client_country: String, client_phone: String, client_postcode : String,client_state: String,
                          userId: Int, total: String, menuIds: ArrayList<String>) : Resource<SaveResponse>

    suspend fun getPaymentUrl(order_id : Int) : Resource<GetPaymentUrl>

    suspend fun updatestatus(order_id : Int, status: String) : Resource<UpdateResponse>

    //suspend fun getCommandes(userId: String) : Resource<WoksRawModel>
}