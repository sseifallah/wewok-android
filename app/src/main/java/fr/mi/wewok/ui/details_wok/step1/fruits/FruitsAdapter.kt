package fr.mi.wewok.ui.details_wok.step1.fruits

import android.content.Context
import fr.mi.wewok.data.model.CommandeModel
import fr.mi.wewok.data.model.FromagesModel
import fr.mi.wewok.data.model.MenuItemRawModel
import fr.mi.wewok.ui.details_wok.ProductWithPriceAdapter

class FruitsAdapter(context: Context, onClickListener:(position:Int) -> Unit): ProductWithPriceAdapter<FromagesModel>(context,onClickListener) {

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val fruit = items[position]
        val title =  fruit.title
        val image =  fruit.image
        val price =  fruit.price
        val selected =  fruit.selected
        holder.bindView(title!!,image,price!!.toFloat(),selected)
        holder.bindEvents(position,onClickListener)
    }

    fun selectOrUnSelect(position: Int) : MenuItemRawModel
    {
        items.mapIndexed { index, model ->
            if (index == position)
                model.selected = ! model.selected
           /* else
                model.selected = false*/
        }
        notifyDataSetChanged()
        return items.get(position)
    }

    fun getItem(position: Int) : MenuItemRawModel = items[position]

    fun unselectItem(id :String){
        items.find {
            it.id.equals(id)
        }?.let {
            it.selected=false
        }
        notifyDataSetChanged()
    }
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