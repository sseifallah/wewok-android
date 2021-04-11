package com.delivery.wewok.ui.details_wok.step1

import android.content.Context
import com.delivery.wewok.base.ext.safeToString
import com.delivery.wewok.data.model.BasesModel
import com.delivery.wewok.data.model.CommandeModel
import com.delivery.wewok.data.model.IngredientItem
import com.delivery.wewok.ui.details_wok.ProductAdapter

class BasesAdapter(context: Context,onClickListener:(position:Int) -> Unit): ProductAdapter<BasesModel>(context,onClickListener) {

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
       val base = items[position]
       val title =  base.name
        val selected =  base.selected
        if (base.image.isNullOrEmpty()){
            holder.bindView(title!!,items[position].image.safeToString(),selected)
        }
        else{
            val image =  base.image
            holder.bindView(title!!,image,selected)
        }

       holder.bindEvents(position,onClickListener)
    }

    fun selectOrUnSelect(position: Int)
    {
        items.mapIndexed { index, basesModel ->
            basesModel.selected = index == position
        }
        notifyDataSetChanged()
    }

    fun getItem(position: Int) : IngredientItem =items[position]

    fun getSelectedItems() : CommandeModel {
        var selected : CommandeModel? = null
        for (item in items){
            if (item.selected == true)
                selected = CommandeModel(item.id!!,item.name!!,item.price!!,item.image,1,true)
        }
        return  selected!!
    }
    fun unselectAll()
    {
        for (item in items)
            item.selected=false
        notifyDataSetChanged()
    }
}