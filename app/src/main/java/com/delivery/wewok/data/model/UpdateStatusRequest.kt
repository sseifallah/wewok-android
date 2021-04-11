package com.delivery.wewok.data.model

import com.google.gson.annotations.SerializedName

class UpdateStatusRequest(
        @SerializedName("order_id")
        val order_id : Int,
        @SerializedName("status")
        val status : String
)