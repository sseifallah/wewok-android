package fr.mi.wewok.data.model

data class FromagesModel(val title:String,val image:String ,val price:Float,var selected:Boolean = false) : ProductWithPriceAdapterItem