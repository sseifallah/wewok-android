package fr.mi.wewok.data.model

import com.google.gson.annotations.SerializedName

data class ResetPasswordApiModel (
        @SerializedName("data")
        val data:StatusModel?,
        @SerializedName("message")
        val message:String)