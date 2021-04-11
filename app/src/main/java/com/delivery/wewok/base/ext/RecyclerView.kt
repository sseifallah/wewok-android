package com.delivery.wewok.base.ext

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.init(
    adapter: RecyclerView.Adapter<*>,
    oriontation:Int,
    hasFixedSize: Boolean = false
){
    setHasFixedSize(hasFixedSize)
    layoutManager = LinearLayoutManager(context,oriontation,false)
    setAdapter(adapter)
}