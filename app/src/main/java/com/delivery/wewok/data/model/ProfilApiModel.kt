package com.delivery.wewok.data.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class ProfilApiModel(
        @SerializedName("status")
        val status: Boolean,
        @SerializedName("data")
        val data:UserData,

)

data class UserData(
        @SerializedName("client_id")
        val client_id: Int,
        @SerializedName("client_name")
        val client_name: String,
        @SerializedName("client_email")
        val client_email: String,
        @SerializedName("client_phone")
        val client_phone: String,
        @SerializedName("client_adress")
        val client_adress: String,
        @SerializedName("client_orders")
        val client_orders: JsonObject
)