package com.delivery.wewok.ui.details_wok.step1.fromage

import android.content.Context
import com.delivery.wewok.data.model.CommandeModel
import com.delivery.wewok.data.model.FromagesModel
import com.delivery.wewok.data.model.IngredientItem
import com.delivery.wewok.data.model.MenuItemRawModel
import com.delivery.wewok.ui.details_wok.ProductWithPriceAdapter

class FromageAdapter(context: Context, onClickListener:(position:Int) -> Unit): ProductWithPriceAdapter<FromagesModel>(context,onClickListener) {

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val fromage = items[position]
        val title =  fromage.title
        val image =  fromage.image
        val price =  fromage.price
        val selected =  fromage.selected
        holder.bindView(title!!,image,price!!.toFloat(),selected)
        holder.bindEvents(position,onClickListener)
    }

    fun selectOrUnSelect(position: Int) : MenuItemRawModel
    {
        items.mapIndexed { index, model ->
            if (index == position)
                model.selected = ! model.selected
            /*else
                model.selected = false*/
        }
        notifyDataSetChanged()
        return items.get(position)
    }

    fun getItem(position: Int) : MenuItemRawModel = items[position]

    fun getSelectedItems() : CommandeModel? {
        var selected : CommandeModel? = null
        for (item in items){
            if (item.selected == true)
                selected = CommandeModel(item.id!!,item.title!!,item.price!!,item.image,1)
        }
        return  selected
    }

    fun unselectAll()
    {
        for (item in items)
            item.selected=false
        notifyDataSetChanged()
    }
}