package fr.mi.wewok.ui.details_wok

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import fr.mi.wewok.R
import fr.mi.wewok.base.ext.setImage
import fr.mi.wewok.data.model.MenuItemRawModel
import fr.mi.wewok.data.model.ProductWithPriceAdapterItem
import kotlinx.android.synthetic.main.item_product_with_price.view.*

abstract class ProductWithPriceAdapter<T : ProductWithPriceAdapterItem>(val context: Context, val onClickListener:(position:Int) -> Unit): RecyclerView.Adapter<ProductWithPriceAdapter.ProductViewHolder>() {

    protected var items = ArrayList<MenuItemRawModel>()

    fun setData(items:List<MenuItemRawModel>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_product_with_price, parent, false)
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
        private val lbl_price = itemView.lbl_price

        @SuppressLint("SetTextI18n")
        fun bindView(title: String, image:String, price:Float, selected:Boolean)
        {
            lblTitle.text = title
            img.setImage(image,title)
            lbl_price.text = "${String.format("%.2f", price)}€"
            if(selected)
            {
                round_rect_view.borderColor = ContextCompat.getColor(context, R.color.orange)
                lblTitle.setTextColor(ContextCompat.getColor(context, R.color.orange))
                lbl_price.setTextColor(ContextCompat.getColor(context, R.color.orange))
            }else
            {
                round_rect_view.borderColor = ContextCompat.getColor(context, R.color.bold_grey)
                lblTitle.setTextColor(ContextCompat.getColor(context, R.color.bold_grey))
                lbl_price.setTextColor(ContextCompat.getColor(context, R.color.bold_grey))
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