package fr.mi.wewok.data.model

import com.google.gson.annotations.SerializedName

data class UpdateResponse(
        @SerializedName("result")
        val result : Boolean,
)