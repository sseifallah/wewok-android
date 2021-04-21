package fr.mi.wewok.ui.details_wok.step1

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.mi.wewok.R
import fr.mi.wewok.base.ext.setImage
import fr.mi.wewok.data.model.CommandeModel

import kotlinx.android.synthetic.main.item_quantite.view.*

class QuantiteAdapter(val context: Context, val setQuantiteGone:() -> Unit ,val unselectIngredient:(id :String,type : Int) -> Unit ) : RecyclerView.Adapter<QuantiteAdapter.CommandeViewHolder>() {

    protected var items = ArrayList<CommandeModel>()
    var qntItems = ArrayList<CommandeModel>()
    fun addData(item:CommandeModel){
        this.items.add(item)
        this.qntItems.add(item)
        Log.i("QNTY_ITEMS"," SET : ${qntItems.size}")
        notifyDataSetChanged()
    }

    fun removeData(id: String){
        this.items.removeAll{
            it.id.equals(id)
        }
        this.qntItems.removeAll{
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
            qntItems.add(items[position])
            Log.i("QNTY_ITEMS"," ADD : ${qntItems.size}")
            holder.txt_qnt.text = items[position].qunatity.toString()
        }
        holder.btn_moin.setOnClickListener {
            if (items[position].qunatity!= 1){
                items[position].qunatity--
                var pos = qntItems.indexOfFirst {
                    it.id ==  items[position].id
                }
                qntItems.removeAt(pos)
                Log.i("QNTY_ITEMS"," REMOVE : ${qntItems.size}")
                holder.txt_qnt.text = items[position].qunatity.toString()
            }
            else {
                unselectIngredient(items[position].id,items[position].type)
                qntItems.remove(items[position])
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
        qntItems.sortBy { it.title }
        return  qntItems
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