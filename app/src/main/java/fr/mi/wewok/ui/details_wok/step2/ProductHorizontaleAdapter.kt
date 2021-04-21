package fr.mi.wewok.ui.details_wok.step2

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import fr.mi.wewok.R
import fr.mi.wewok.utils.ItemClickListener
import kotlinx.android.synthetic.main.item_product_horizontale.view.*

class ProductHorizontaleAdapter(val context: Context, val itemClickListener: ItemClickListener): RecyclerView.Adapter<ProductHorizontaleAdapter.ProductViewHolder>() {

    var images = ArrayList<Int>()
    var titles = ArrayList<String>()
    var prices = ArrayList<Float>()
    var selectedItems = ArrayList<Boolean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_product_horizontale, parent, false)
        return ProductViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int)
    {
        val title = titles[position]
        val image = images[position]
        val price = prices[position]
        val isItemSelected = selectedItems[position]
        holder.lblTitle.text = title
        holder.img.setImageResource(image)
        holder.lblPrice.text = "${price}€"
        if(isItemSelected)
        {
            holder.round_rect_view.borderColor = ContextCompat.getColor(context, R.color.orange)
            holder.lblTitle.setTextColor(ContextCompat.getColor(context, R.color.orange))
        }else
        {
            holder.round_rect_view.borderColor = ContextCompat.getColor(context, R.color.bold_grey)
            holder.lblTitle.setTextColor(ContextCompat.getColor(context, R.color.bold_grey))
        }
        holder.view.setOnClickListener {
            itemClickListener.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return  images.size
    }

    class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val view = itemView
        val lblTitle = itemView.lbl_title
        val lblPrice = itemView.lbl_price
        val img = itemView.img
        val round_rect_view = itemView.round_rect_view
    }
}