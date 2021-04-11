package com.delivery.wewok.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BasesModel(var id : String ,val name:String,val image:String, var price : String,var selected : Boolean = false) : ProductAdapterItem , Parcelable