 package fr.mi.wewok.ui.details_wok.commande

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import fr.mi.wewok.R
import fr.mi.wewok.base.ext.init
import fr.mi.wewok.data.model.CommandeModel
import fr.mi.wewok.ui.details_wok.payement.PaymentEmporterActivity
import fr.mi.wewok.ui.details_wok.payement.PaymentLivraisonActivity
import fr.mi.wewok.ui.details_wok.step1.DetailsWokActivityStep1
import fr.mi.wewok.ui.details_wok.step2.DetailsWokActivityStep2
import fr.mi.wewok.ui.details_wok.step3.DetailsWokActivityStep3
import fr.mi.wewok.utils.MODE_WOK
import fr.mi.wewok.utils.SAVED_ORDERS
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_commande.*

 @AndroidEntryPoint
class CommandeActivity : AppCompatActivity() {

    companion object{
        var  menuIds = ArrayList<String>()
        var total = 0.0
        var allCommandes = ArrayList<CommandeModel>()
        var commandes = ArrayList<CommandeModel>()
    }
    lateinit var adapter : CommandeAdapter
    private val viewModel: CommandesViewModel by viewModels()
    var userId = 0
    var mode=""
     var bottom = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("CMD_ACT","onCreate")
        mode = Paper.book().read(MODE_WOK)
        menuIds = ArrayList<String>()
        total = 0.0
        allCommandes = ArrayList<CommandeModel>()
        commandes = ArrayList<CommandeModel>()
        setContentView(R.layout.activity_commande)

     //   userId = Paper.book().read<Int>(USER_ID)
        getSavedOrders()
        initRecycler()
        getNewOrders()
        initUi()
        setupNavigation()

    }

     override fun onResume() {
         Log.i("CMD_ACT","onResume")
         super.onResume()
     }


    fun getSavedOrders(){
        if (Paper.book().contains(SAVED_ORDERS)){
            var savedOrders = Paper.book().read<ArrayList<CommandeModel>>(SAVED_ORDERS)
            Log.i("CMD_ACT","saved : $savedOrders")
            if (! savedOrders.isNullOrEmpty())
                allCommandes.addAll(savedOrders)
        }
    }

    private fun initRecycler() {
        adapter = CommandeAdapter(this,this::removeItem,this::addItem,this::addNewCommande)
        recycler_commande.init(adapter, LinearLayoutManager.VERTICAL)
    }

    fun getNewOrders(){
        allCommandes.addAll(DetailsWokActivityStep1.commandes)
        allCommandes.addAll(DetailsWokActivityStep2.commandes)
        allCommandes.addAll(DetailsWokActivityStep3.commandes)
    }

    fun initUi(){
        if (! allCommandes.isNullOrEmpty()) {
            commandes.addAll(allCommandes)
            adapter.setData(commandes)
            btn_next_cmd.setBackgroundResource(R.drawable.shape_top_corners_button)
            btn_next_cmd.isClickable = true
            btn_next_cmd.isEnabled = true
            updateValues()
        }
        else
        {
            btn_next_cmd.setBackgroundResource(R.drawable.shape_top_corners_button_grey)
            btn_next_cmd.isClickable = false
            btn_next_cmd.isEnabled = false
        }
    }


    private fun setupNavigation() {

        btn_next_cmd.setOnClickListener {
            menuIds = ArrayList<String>()
            //Log.i("CMD_ID", " CMD ")
            for (item in commandes) {
                if (item.wok == true) {
                    Log.i("CMD_ID", " true")
                    var wokIds = item.id.split(",").toTypedArray()
                    Log.i("CMD_ID", " ID SPLT $wokIds")
                    for(id in wokIds)
                        if (id.contains("extra-0-0", ignoreCase = true)) {
                            Log.i("CMD_ID", " : 0-0 ${id} : ${title}")
                            menuIds.add("extra-0")
                            menuIds.add("extra-0-0")
                            menuIds.add(item.id)
                        } else if (id.contains("extra-0-1", ignoreCase = true)) {
                            Log.i("CMD_ID", " : 0-1 ${id} : ${title}")
                            menuIds.add("extra-0")
                            menuIds.add("extra-0-1")
                            menuIds.add(item.id)
                        } else if (id.contains("extra-1-0", ignoreCase = true)) {
                            Log.i("CMD_ID", " : 1-0 ${id} : ${title}")
                            menuIds.add("extra-1")
                            menuIds.add("extra-1-0")
                            menuIds.add(item.id)
                        } else {
                            Log.i("CMD_ID", " : else ${id} : ${title}")
                            menuIds.add(item.id)
                        }
                }
                else {
                    Log.i("CMD_ID", " : else false ${item.id} : ${item.title}  ${item.wok}")
                    menuIds.add(item.id)
                }
            }

            Log.i("CMD_ID_ALL",  menuIds.toString());

            toPayment()
        }
    }

    fun removeItem(id : String){
        commandes.removeAll {
            it.id == id
        }
        updateValues()
    }

     fun addItem(item : CommandeModel,index : Int){
         commandes.add(index,item)
         updateValues()
     }

    fun updateValues(){

        total = 0.0
        for (item in adapter.getItems()){
            var priceWithoutComma = item.price.replace(",",".")
            var pr = priceWithoutComma.toDouble()
            var prix = String.format("%.2f", pr)
            var prixWithoutComma = prix.replace(",",".")
            total += prixWithoutComma.toDouble()
        }
        var price = String.format("%.2f", total)

        txt_top.text = "${adapter.getItems().size} articles / Total panier : " + String.format("%.2f", total) + "€"
        txt_price.text = "$price€"
        if(mode.equals("MODE_EMPORTER")) {
            var tax = String.format("%.2f", total/10)
            txt_tax.text = "Dont TVA 10% : $tax€"
        }
        else if(mode.equals("MODE_LIVRAISON")) {
            var tax = String.format("%.2f", total/10)
            txt_tax.text = "Dont TVA 10% : $tax€"
        }

    }

     fun addNewCommande(){
        // if(!commandes.isNullOrEmpty()) {
             Log.i("CMD_ACT", "allCommandes size : ${commandes.size}")
             for (com in commandes)
                 Log.i("CMD_ACT", "allCommandes : ${com.title}")
             Paper.book().write(SAVED_ORDERS, commandes)
             commandes.clear()
             allCommandes.clear()
             DetailsWokActivityStep1.commandes.clear()
             DetailsWokActivityStep1.adapterRecyclerViewRetirerIngredient.unselectAll()
             DetailsWokActivityStep1.adapterRecyclerViewToppings.unselectAll()
             DetailsWokActivityStep1.adapterRecyclerViewVotreBase.unselectAll()
             DetailsWokActivityStep1.proteinFrag.unselectAll()
             DetailsWokActivityStep1.fromageFrag.unselectAll()
             DetailsWokActivityStep1.fruitsFrag.unselectAll()
             DetailsWokActivityStep1.autreFrag.unselectAll()
             DetailsWokActivityStep2.commandes.clear()
             DetailsWokActivityStep2.adapter.unselectAll()
             DetailsWokActivityStep3.commandes.clear()
             DetailsWokActivityStep3.adapter.unselectAll()
             startActivity(Intent(this, DetailsWokActivityStep1::class.java))
         //}
     }
    fun toPayment(){
        //toast(" mode : $mode")
        if(mode.equals("MODE_EMPORTER")) {
            var intent = Intent(this, PaymentEmporterActivity::class.java)
            intent.putExtra("TOTAL", total)
            startActivity(intent)
        }
        else if(mode.equals("MODE_LIVRAISON")) {
            var intent = Intent(this, PaymentLivraisonActivity::class.java)
            intent.putExtra("TOTAL", total)
            startActivity(intent)
        }
    }

    fun clearCommandes(){
        commandes.clear()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, 0)
    }
}
