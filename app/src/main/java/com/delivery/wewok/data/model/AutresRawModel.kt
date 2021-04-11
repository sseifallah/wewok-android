package com.delivery.wewok.data.model

import com.google.gson.annotations.SerializedName

data class AutresRawModel(
    @SerializedName("Autres ")
    val autres:Map<String, MenuItemRawModel?>
)