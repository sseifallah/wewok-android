package com.delivery.wewok.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConnectModelR(
        val status:String,
        val code:Int,
        var user:UserModel?) : Parcelable