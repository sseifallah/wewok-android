package com.delivery.wewok.data.model

import android.os.Message
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ZoneModel(
        val status: Boolean,
        val frais: String,
        val message: String,

): Parcelable

