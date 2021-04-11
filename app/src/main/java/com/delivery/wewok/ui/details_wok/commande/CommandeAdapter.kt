package com.delivery.wewok.ui.details_wok.commande

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.delivery.wewok.R
import com.delivery.wewok.base.ext.setImage
import com.delivery.wewok.data.model.CommandeModel
import com.delivery.wewok.data.model.IngredientItem
import com.delivery.wewok.data.model.Ingredients
import com.delivery.wewok.data.model.ProductAdapterItem
import kotlinx.android.synthetic.main.item_commande.view.*
import kotlinx.android.synthetic.main.item_product.view.*
import kotlinx.android.synthetic.main.item_product.view.img
import kotlinx.android.synthetic.main.item_product.view.lbl_title
import kotlinx.android.synthetic.main.item_product.view.round_rect_view

class CommandeAdapter (val context: Context,val updateValues:(id : String) -> Unit): RecyclerView.Adapter<CommandeAdapter.CommandeViewHolder>() {

    protected var items = ArrayList<CommandeModel>()

    fun setData(items:List<CommandeModel>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun getItems() : List<CommandeModel>{
        return  items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommandeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_commande, parent, false)
        return CommandeViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: CommandeViewHolder, position: Int) {
        holder.price.text =  items[position].price + " €"
        holder.lblTitle.text = items[position].title
        holder.img.setImage(items[position].image,items[position].title)

        holder.delete.setOnClickListener {
            var id = items[position].id
            items.removeAt(position)
            updateValues(id)
            notifyDataSetChanged()

        }
    }

    override fun getItemCount(): Int {
      return  items.size
    }

    class CommandeViewHolder(itemView: View,val context: Context): RecyclerView.ViewHolder(itemView){
        val view = itemView
        val lblTitle = itemView.lbl_title
        val img = itemView.img
        val round_rect_view = itemView.round_rect_view
         val delete = itemView.btn_delete
         val price = itemView.lbl_prix

       fun bindView(title: String,image:String,selected:Boolean)
       {
           lblTitle.text = title
           img.setImage(image,title)

       }

       fun bindEvents(position:Int,onClickListener:(position:Int) -> Unit)
       {
           delete.setOnClickListener {

           }
       }
    }
}