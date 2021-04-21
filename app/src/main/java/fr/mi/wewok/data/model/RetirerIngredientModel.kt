package fr.mi.wewok.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class RetirerIngredientModel(var id :String ,val name:String, var price : String ,val image:String,var selected:Boolean=false):ProductAdapterItem , Parcelable