package com.delivery.wewok.data.model

data class AutresModel(val title:String, val image:String, val price:Float, var selected:Boolean = false) : ProductWithPriceAdapterItem