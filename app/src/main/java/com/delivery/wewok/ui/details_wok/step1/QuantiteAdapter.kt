package com.delivery.wewok.ui.details_wok.step1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.delivery.wewok.R
import com.delivery.wewok.base.ext.setImage
import com.delivery.wewok.data.model.CommandeModel

import kotlinx.android.synthetic.main.item_quantite.view.*

class QuantiteAdapter(val context: Context, val setQuantiteGone:() -> Unit ,val unselectProteine:(id :String) -> Unit ) : RecyclerView.Adapter<QuantiteAdapter.CommandeViewHolder>() {

    protected var items = ArrayList<CommandeModel>()
    fun addData(item:CommandeModel){
        this.items.add(item)
        notifyDataSetChanged()
    }

    fun removeData(id: String){
        this.items.removeAll{
            it.id.equals(id)
        }
        if (this.items.size==0){
            setQuantiteGone()
        }
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommandeViewHolder {
        val view: View
        view =  LayoutInflater.from(context).inflate(R.layout.item_quantite, parent, false)
        return CommandeViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CommandeViewHolder, position: Int) {

        holder.txt_qnt.text = items[position].qunatity.toString()
        holder.txt_title.text = items[position].title
        holder.img.setImage( items[position].image, "")
        holder.btn_plus.setOnClickListener {
            items[position].qunatity++
            holder.txt_qnt.text = items[position].qunatity.toString()
        }
        holder.btn_moin.setOnClickListener {
            if (items[position].qunatity!= 1){
                items[position].qunatity--
                holder.txt_qnt.text = items[position].qunatity.toString()
            }
            else {
                unselectProteine(items[position].id)
                items.remove(items[position])
                if (this.items.size==0){
                    setQuantiteGone()
                }
                notifyDataSetChanged()
            }
        }

    }


    fun getItem(position: Int) : CommandeModel = items[position]

    fun getAllItems() : ArrayList<CommandeModel>? {
        return  items
    }

    fun deleteAll()
    {
        items = ArrayList<CommandeModel>()
        notifyDataSetChanged()
        setQuantiteGone()
    }
    class CommandeViewHolder(itemView: View, val context: Context): RecyclerView.ViewHolder(itemView){
        val view = itemView
        val txt_qnt = itemView.txt_quantite
        val txt_title = itemView.txt_graniture
        val btn_plus  = itemView.btn_plus
        val btn_moin  = itemView.btn_minus
        val img = itemView.icon_graniture

    }



}