package fr.mi.wewok.ui.details_wok.commande

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.mi.wewok.R
import fr.mi.wewok.base.ext.setImage
import fr.mi.wewok.data.model.CommandeModel
import kotlinx.android.synthetic.main.item_button_add.view.*
import kotlinx.android.synthetic.main.item_commande.view.*
import kotlinx.android.synthetic.main.item_product.view.img
import kotlinx.android.synthetic.main.item_product.view.lbl_title
import kotlinx.android.synthetic.main.item_product.view.round_rect_view


class CommandeAdapter (val context: Context,val removeItem:(id : String) -> Unit,val addItem:(item : CommandeModel,pos : Int) -> Unit,val addNewCommande:() -> Unit): RecyclerView.Adapter<CommandeAdapter.CommandeViewHolder>() {

    protected var items = ArrayList<CommandeModel>()

    fun setData(items:List<CommandeModel>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun getItems() : List<CommandeModel>{
        return  items
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == items.size) R.layout.item_button_add else R.layout.item_commande
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommandeViewHolder {
        val view: View

        if (viewType == R.layout.item_commande) {
            view =  LayoutInflater.from(context).inflate(R.layout.item_commande, parent, false)
        }
        else
            view =  LayoutInflater.from(context).inflate(R.layout.item_button_add, parent, false)
       // val view = LayoutInflater.from(context).inflate(R.layout.item_commande, parent, false)
        return CommandeViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: CommandeViewHolder, position: Int) {
        if(position != items.size) {
            holder.price.text = items[position].price + " €"
            holder.lblTitle.text = items[position].title
            holder.img.setImage(items[position].image, items[position].title)
            holder.delete.setOnClickListener {
                var id = items[position].id
                items.removeAt(position)
                removeItem(id)
                notifyDataSetChanged()
            }
            holder.bnt_add_ing.setOnClickListener {
                var item = items[position]
                items.add(position+1 , item)
                addItem(item,position+1)
                notifyDataSetChanged()
            }
        }else{
            holder.btn_add.setOnClickListener {
                addNewCommande()
            }
        }
    }

    override fun getItemCount(): Int {
      return  items.size +1
    }

    class CommandeViewHolder(itemView: View,val context: Context): RecyclerView.ViewHolder(itemView){
        val view = itemView
        val lblTitle = itemView.lbl_title
        val img = itemView.img
        val round_rect_view = itemView.round_rect_view
        val delete = itemView.btn_delete
        val price = itemView.lbl_prix
        val bnt_add_ing = itemView.btn_add_ing
        val btn_add =  itemView.btn_add


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