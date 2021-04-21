package fr.mi.wewok.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegisterModelR(
    val code:Int,
    var message:String) : Parcelable
