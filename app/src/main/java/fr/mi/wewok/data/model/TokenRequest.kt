package fr.mi.wewok.data.model

import com.google.gson.annotations.SerializedName

data class TokenRequest (
        @SerializedName("client_id")
        val client_id : Int,
        @SerializedName("token")
        val token : String,
        @SerializedName("device")
        val device : String,

)