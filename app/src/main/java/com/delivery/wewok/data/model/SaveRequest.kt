package com.delivery.wewok.data.model

import com.google.gson.annotations.SerializedName

data class SaveRequest (
        @SerializedName("client_adress")
        val client_adress : String,
        @SerializedName("client_city")
        val client_city : String,
        @SerializedName("client_country")
        val client_country : String,
        @SerializedName("client_phone")
        val client_phone : String,
        @SerializedName("client_postcode")
        val client_postcode : String,
        @SerializedName("client_state")
        val client_state : String,
        @SerializedName("id_client")
        val id_client : Int,
        @SerializedName("menus")
        val menus:ArrayList<String>,
        @SerializedName("total_commande")
        val total_commande:String,

)