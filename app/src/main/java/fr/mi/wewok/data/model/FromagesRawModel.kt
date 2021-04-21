package fr.mi.wewok.data.model

import com.google.gson.annotations.SerializedName

data class FromagesRawModel(
    @SerializedName("Fromages ")
    val formages:Map<String, MenuItemRawModel?>
)