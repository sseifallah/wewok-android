package fr.mi.wewok.data.model

import com.google.gson.annotations.SerializedName

data class BoissonsRawModel(
    @SerializedName("boissons")
    val boissons:Map<String, MenuItemRawModel?>
)