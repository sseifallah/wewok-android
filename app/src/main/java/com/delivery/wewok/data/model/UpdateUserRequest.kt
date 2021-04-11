package com.delivery.wewok.data.model

import com.google.gson.annotations.SerializedName

class UpdateUserRequest (
        @SerializedName("id")
        val client_id: Int,
        @SerializedName("first_name")
        val first_name: String,
        @SerializedName("last_name")
        val last_name: String,
        @SerializedName("mail")
        val client_email: String,
        @SerializedName("phone")
        val client_phone: String,
        @SerializedName("adress_1")
        val adresse: String,
        @SerializedName("city")
        val ville: String,
        @SerializedName("postcode")
        val postal_code: String,

)