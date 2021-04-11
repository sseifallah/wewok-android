package com.delivery.wewok.data.model

import com.google.gson.annotations.SerializedName

data class ToppingsRawModel(
    @SerializedName("choix-toppings")
    val toppings:Map<String, MenuItemRawModel?>
)