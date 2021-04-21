package fr.mi.wewok.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConnectModelR(
        val status:String,
        val code:Int,
        var user:UserModel?) : Parcelable