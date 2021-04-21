package fr.mi.wewok.data.model

import com.google.gson.annotations.SerializedName

data class ZoneApiModel (
        @SerializedName("status")
        val status:Boolean,
        @SerializedName("zip_code")
        val zip_code:String,
        @SerializedName("zone")
        val zone:String,
        @SerializedName("frais")
        val frais:String,
        @SerializedName("cod")
        val cod:String,
        @SerializedName("error")
        val error:String,

)