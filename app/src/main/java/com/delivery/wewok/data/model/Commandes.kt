package com.delivery.wewok.data.model

import com.google.gson.annotations.SerializedName

class Commandes(
        @SerializedName("order_id")
        var id : String? = null,
        @SerializedName("order_date")
        var date : String? = null,
        @SerializedName("order_price")
        var price : String? = null,
        @SerializedName("order_status")
        var status: String? = null)