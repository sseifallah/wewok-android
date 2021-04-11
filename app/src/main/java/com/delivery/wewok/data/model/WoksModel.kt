package com.delivery.wewok.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WoksModel(
    val title: String,
    val image: String,
    val description: String,
    val price : Float,
    var bases : ArrayList<BasesModel> = ArrayList(),
    var retirerIngredients: ArrayList<RetirerIngredientModel> = ArrayList()
    ): Parcelable