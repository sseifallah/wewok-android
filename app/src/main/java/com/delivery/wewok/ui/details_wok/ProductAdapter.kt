package com.delivery.wewok.ui.details_wok

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.delivery.wewok.R
import com.delivery.wewok.base.ext.setImage
import com.delivery.wewok.data.model.IngredientItem
import com.delivery.wewok.data.model.Ingredients
import com.delivery.wewok.data.model.ProductAdapterItem
import kotlinx.android.synthetic.main.item_product.view.*

abstract class ProductAdapter<T : ProductAdapterItem>(val context: Context,val onClickListener:(position:Int) -> Unit): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    protected var items = ArrayList<IngredientItem>()

    fun setData(items:List<IngredientItem>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view, context)
    }

    override fun getItemCount(): Int {
      return  items.size
    }

    class ProductViewHolder(itemView: View,val context: Context): RecyclerView.ViewHolder(itemView){
       private val view = itemView
       private val lblTitle = itemView.lbl_title
       private val img = itemView.img
       private val round_rect_view = itemView.round_rect_view

       fun bindView(title: String,image:String,selected:Boolean)
       {
           lblTitle.text = title
           img.setImage(image,title)
           if(selected)
           {
               round_rect_view.borderColor = ContextCompat.getColor(context, R.color.orange)
               lblTitle.setTextColor(ContextCompat.getColor(context, R.color.orange))
           }else
           {
               round_rect_view.borderColor = ContextCompat.getColor(context, R.color.bold_grey)
               lblTitle.setTextColor(ContextCompat.getColor(context, R.color.bold_grey))
           }
       }

       fun bindEvents(position:Int,onClickListener:(position:Int) -> Unit)
       {
           view.setOnClickListener {
               onClickListener.invoke(position)
           }
       }
    }
}