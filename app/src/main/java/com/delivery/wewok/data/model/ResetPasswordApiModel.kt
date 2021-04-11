package com.delivery.wewok.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ResetPasswordApiModel (
        @SerializedName("data")
        val data:StatusModel?,
        @SerializedName("message")
        val message:String)