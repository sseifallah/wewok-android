package fr.mi.wewok.data.model

import com.google.gson.annotations.SerializedName

class GetUrlRequest (
    @SerializedName("order_id")
    val order_id : Int
)