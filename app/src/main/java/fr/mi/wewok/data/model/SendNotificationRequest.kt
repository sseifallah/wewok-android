package fr.mi.wewok.data.model

import com.google.gson.annotations.SerializedName

data class SendNotificationRequest (
        @SerializedName("client_id")
        val client_id : Int,
        @SerializedName("title")
        val title : String,
        @SerializedName("message")
        val message : String,

)