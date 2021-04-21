package fr.mi.wewok.ui.home.commandes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.mi.wewok.R
import fr.mi.wewok.data.model.*
import kotlinx.android.synthetic.main.item_commandes.view.*

class CommandesAdapter (val context: Context): RecyclerView.Adapter<CommandesAdapter.CommandesViewHolder>() {

    protected var items = ArrayList<Commandes>()

    fun setData(items:List<Commandes>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun getItems() : List<Commandes>{
        return  items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommandesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_commandes, parent, false)
        return CommandesViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: CommandesViewHolder, position: Int) {
        holder.txtDate.text = "Date de la commande: "+items[position].date
        holder.txtNum.text = "Numéro de la commande: "+items[position].id
        holder.txtStatus.text = "Status: "+items[position].status
        holder.txtPrice.text = items[position].price
    }

    override fun getItemCount(): Int {
      return  items.size
    }

    class CommandesViewHolder(itemView: View,val context: Context): RecyclerView.ViewHolder(itemView){
        val view = itemView
        val txtDate = itemView.txt_date
        val txtNum = itemView.txt_num
        val txtStatus = itemView.txt_status
        val txtPrice = itemView.txt_price

    }
}