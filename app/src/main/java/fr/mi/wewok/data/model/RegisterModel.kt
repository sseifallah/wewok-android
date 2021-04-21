package fr.mi.wewok.data.model

import com.google.gson.annotations.SerializedName

data class RegisterModel (
    @SerializedName("code")
    val code:Int,
    @SerializedName("message")
    val message:String)