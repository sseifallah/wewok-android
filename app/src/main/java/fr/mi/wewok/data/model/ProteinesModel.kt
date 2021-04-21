package fr.mi.wewok.data.model

data class ProteinesModel(val title:String,val image:String ,val price:Float,var selected:Boolean = false) : ProductWithPriceAdapterItem