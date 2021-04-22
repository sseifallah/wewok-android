package fr.mi.wewok.ui.details_wok.step1

import android.content.Context
import fr.mi.wewok.data.model.CommandeModel
import fr.mi.wewok.data.model.MenuItemRawModel
import fr.mi.wewok.data.model.ToppingsModel
import fr.mi.wewok.ui.details_wok.ProductWithPriceAdapter

class ToppingsAdapter(context: Context, onClickListener:(position:Int) -> Unit): ProductWithPriceAdapter<ToppingsModel>(context,onClickListener) {

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val topping = items[position]
        val title =  topping.title
        val image =  topping.image
        val price =  topping.price
        val selected =  topping.selected
        holder.bindView(title!!,image,price!!.toFloat(),selected)
        holder.bindEvents(position,onClickListener)
    }

    fun selectOrUnSelect(position: Int)
    {
        val toppingModel = getItem(position)
        toppingModel.selected = !toppingModel.selected
        notifyDataSetChanged()
    }

    fun getItem(position: Int) : MenuItemRawModel = items[position]

    fun getSelectedItems() : ArrayList<CommandeModel> {
        var selected = ArrayList<CommandeModel>()
        for (item in items){
            if (item.selected == true)
                selected.add(CommandeModel(item.id!!,item.title!!,item.price!!,item.image,1,false))
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