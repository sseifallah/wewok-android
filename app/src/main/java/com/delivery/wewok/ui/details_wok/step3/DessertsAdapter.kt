package com.delivery.wewok.ui.details_wok.step3

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.delivery.wewok.R
import com.delivery.wewok.base.ext.setImage
import com.delivery.wewok.data.model.BoissonsModel
import com.delivery.wewok.data.model.DessertsModel
import com.delivery.wewok.data.model.MenuItemRawModel
import kotlinx.android.synthetic.main.item_product_horizontale.view.*

class DessertsAdapter(val context: Context, val onClickListener:(position:Int) -> Unit): RecyclerView.Adapter<DessertsAdapter.ViewHolder>(){

    protected var items = ArrayList<MenuItemRawModel>()

    fun setData(items:List<MenuItemRawModel>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_product_horizontale, parent, false)
        return ViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return  items.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = items[position]
        val title =  model.title
        val image =  model.image
        val price = model.price
        val selected =  model.selected
        holder.bindView(title!!,image,price!!.toFloat(),selected)
        holder.bindEvents(position,onClickListener)
    }

    fun selectOrUnSelect(position: Int) : MenuItemRawModel
    {
        val model = items[position]
        model.selected = !model.selected
        notifyDataSetChanged()
        return model
    }

    fun unselectAll()
    {
        for (item in items)
            item.selected=false
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View, val context: Context): RecyclerView.ViewHolder(itemView){
        private val view = itemView
        private val lblTitle = itemView.lbl_title
        private val lbl_price = itemView.lbl_price
        private val img = itemView.img
        private val round_rect_view = itemView.round_rect_view

        @SuppressLint("SetTextI18n")
        fun bindView(title: String, image:String,price:Float,selected:Boolean)
        {
            lblTitle.text = title
            img.setImage(image,title)
            lbl_price.text = "${String.format("%.2f", price)}€"
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