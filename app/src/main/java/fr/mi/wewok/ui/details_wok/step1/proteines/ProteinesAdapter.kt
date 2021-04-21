package fr.mi.wewok.ui.details_wok.step1.proteines

import android.content.Context
import fr.mi.wewok.data.model.CommandeModel
import fr.mi.wewok.data.model.MenuItemRawModel
import fr.mi.wewok.data.model.ProteinesModel
import fr.mi.wewok.ui.details_wok.ProductWithPriceAdapter

class ProteinesAdapter(context: Context, onClickListener:(position:Int) -> Unit): ProductWithPriceAdapter<ProteinesModel>(context,onClickListener) {

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val proteine = items[position]
        val title =  proteine.title
        val image =  proteine.image
        val price =  proteine.price
        val selected =  proteine.selected
        holder.bindView(title!!,image,price!!.toFloat(),selected)
        holder.bindEvents(position,onClickListener)
    }

    fun selectOrUnSelect(position: Int) : MenuItemRawModel
    {
        items.mapIndexed { index, proteineModel ->
            if (index == position)
                proteineModel.selected = ! proteineModel.selected
            /*else
                proteineModel.selected = false*/
        }
      //  Log.i("PROTEINS_SELECTED",items.get(position).title.toString())
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

    fun unselectItem(id :String){
        items.find {
            it.id.equals(id)
        }?.let {
            it.selected=false
        }
        notifyDataSetChanged()
    }

    fun unselectAll()
    {
        for (item in items)
            item.selected=false
        notifyDataSetChanged()
    }
}