package com.delivery.wewok.data.model

import com.google.gson.annotations.SerializedName

data class DessertsRawModel(
    @SerializedName("desserts")
    val desserts:Map<String, MenuItemRawModel?>
)