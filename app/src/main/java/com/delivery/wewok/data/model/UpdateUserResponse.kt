package com.delivery.wewok.data.model

import com.google.gson.annotations.SerializedName

class UpdateUserResponse (
        @SerializedName("result")
        val result: Boolean,
        @SerializedName("result_code")
        val result_code: Int,
        @SerializedName("user_id")
        val user_id: Int
)