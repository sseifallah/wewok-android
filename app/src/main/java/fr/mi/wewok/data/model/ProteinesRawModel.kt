package fr.mi.wewok.data.model

import com.google.gson.annotations.SerializedName

data class ProteinesRawModel(

    @SerializedName("mes-garnitures-")
    val mesGarnitures:Map<String, MenuItemRawModel?>
)