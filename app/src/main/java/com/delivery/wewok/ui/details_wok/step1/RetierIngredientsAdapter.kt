package com.delivery.wewok.ui.details_wok.step1

import android.content.Context
import com.delivery.wewok.base.ext.safeToString
import com.delivery.wewok.data.model.CommandeModel
import com.delivery.wewok.data.model.IngredientItem
import com.delivery.wewok.data.model.RetirerIngredientModel
import com.delivery.wewok.ui.details_wok.ProductAdapter

class RetierIngredientsAdapter(context: Context, onClickListener:(position:Int) -> Unit): ProductAdapter<RetirerIngredientModel>(context,onClickListener) {

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val retirerIngredient = items[position]
        val title =  retirerIngredient.name
       // val image = retirerIngredient.image
        val selected =  retirerIngredient.selected
        if (retirerIngredient.image.isNullOrEmpty()){
            holder.bindView(title!!,items[position].image.safeToString(),selected)
        }
        else{
            val image =  retirerIngredient.image
            holder.bindView(title!!,image,selected)
        }
    //    holder.bindView(title!!,image,selected)
        holder.bindEvents(position,onClickListener)
    }

    fun selectOrUnSelect(position: Int)
    {
        val retirerIngredient = getItem(position)
        retirerIngredient.selected = !retirerIngredient.selected
        notifyDataSetChanged()
    }

    fun getItem(position: Int) : IngredientItem = items[position]

    fun getSelectedItems() : ArrayList<CommandeModel> {
        var selected = ArrayList<CommandeModel>()
        for (item in items){
            if (item.selected == true)
                selected.add(CommandeModel(item.id!!,item.name!!,item.price!!,item.image,1,true))
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