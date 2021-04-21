package fr.mi.wewok.data.model

import com.google.gson.annotations.SerializedName

data class UserApiModel(

        @SerializedName("result")
        val status: Boolean,
        @SerializedName("result_code")
        val result_code: Int,
        @SerializedName("user")
        val data:GetUserData,

)

data class GetUserData(
        @SerializedName("id")
        val client_id: Int,
        @SerializedName("mail")
        val client_email: String,
        @SerializedName("first_name")
        val first_name: String,
        @SerializedName("last_name")
        val last_name: String,
        @SerializedName("adress_1")
        val adresse: String,
        @SerializedName("city")
        val ville: String,
        @SerializedName("postal_code")
        val postal_code: String,
        @SerializedName("country")
        val country: String,
        @SerializedName("state")
        val state: String,
        @SerializedName("phone")
        val client_phone: String,
        @SerializedName("user_orders")
        val client_orders: List<Commandes>
)