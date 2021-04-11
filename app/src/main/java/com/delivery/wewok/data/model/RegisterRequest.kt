package com.delivery.wewok.data.model

import com.google.gson.annotations.SerializedName

data class RegisterRequest (
    @SerializedName("nom") val nom: String?,
    @SerializedName("prenom") val prenom: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("telephone") val telephone: String?,
    @SerializedName("password") val password: String?,
    @SerializedName("adresse") val adresse: String?

)