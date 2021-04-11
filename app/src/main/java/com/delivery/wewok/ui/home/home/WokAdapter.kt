package com.delivery.wewok.ui.home.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.delivery.wewok.R
import com.delivery.wewok.base.ext.setImage
import com.delivery.wewok.data.model.MenuItemRawModel
import com.delivery.wewok.data.model.WoksModel
import kotlinx.android.synthetic.main.item_wok.view.*

class WokAdapter (val context: Context,val onClickListener:(position:Int) -> Unit): RecyclerView.Adapter<WokAdapter.WokViewHolder>(){

    private var items = ArrayList<MenuItemRawModel>()

    fun setData(items:List<MenuItemRawModel>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun getItem(position:Int):MenuItemRawModel{
        return items[position]
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WokViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_wok, parent, false)
        return WokViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: WokViewHolder, position: Int) {
        val model = items[position]
        val title =  model.title
        val image =  model.image
        val description =  model.description
        val price =  model.price
        holder.bindView(title!!,image!!,description!!,price!!.toFloat())
        holder.bindEvents(position,onClickListener)
    }

    override fun getItemCount(): Int {
        return  items.size
    }

    class WokViewHolder(itemView: View, val context: Context): RecyclerView.ViewHolder(itemView){
        private val view = itemView
        private val lbl_title = itemView.lbl_title
        private val lbl_description = itemView.lbl_description
        private val lbl_price = itemView.lbl_price
        private val roundedImageView = itemView.roundedImageView

        @SuppressLint("SetTextI18n")
        fun bindView(title: String, image:String, description:String, price:Float){
            lbl_title.text =  title
            roundedImageView.setImage(image,title)
            lbl_description.text = description
            lbl_price.text = "${String.format("%.2f", price)}€"
        }

        fun bindEvents(position:Int,onClickListener:(position:Int) -> Unit)
        {
            view.setOnClickListener {
                onClickListener.invoke(position)
            }
        }
    }
}