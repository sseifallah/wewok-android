package fr.mi.wewok.data.model

import com.google.gson.annotations.SerializedName

data class ConnectModel(
        @SerializedName("status")
        val status:String,
        @SerializedName("error")
        val error:String,
        @SerializedName("code")
        val code:Int,
        @SerializedName("cookie")
        val cookie:String ,
        @SerializedName("cookie_name")
        val cookie_name:String,
        @SerializedName("user")
        var user:UserModel?)