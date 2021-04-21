package fr.mi.wewok.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ZoneModel(
        val status: Boolean,
        val frais: String,
        val message: String,

): Parcelable

