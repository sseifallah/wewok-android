package com.delivery.wewok.data.model

import com.google.gson.annotations.SerializedName

data class SaveResponse (
    @SerializedName("status")
    val status : Boolean,
    @SerializedName("order_id")
    val order_id:Int

)
