package com.delivery.wewok.ui.details_wok.commande

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.delivery.wewok.R
import com.delivery.wewok.base.ext.init
import com.delivery.wewok.base.ext.toast
import com.delivery.wewok.data.model.CommandeModel
import com.delivery.wewok.ui.details_wok.payement.PaymentEmporterActivity
import com.delivery.wewok.ui.details_wok.payement.PaymentLivraisonActivity
import com.delivery.wewok.ui.details_wok.step1.DetailsWokActivityStep1
import com.delivery.wewok.ui.details_wok.step2.DetailsWokActivityStep2
import com.delivery.wewok.ui.details_wok.step3.DetailsWokActivityStep3
import com.delivery.wewok.utils.MODE_EMPORTER
import com.delivery.wewok.utils.MODE_WOK
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_commande.*
import kotlinx.android.synthetic.main.activity_commande.btn_next

@AndroidEntryPoint
class CommandeActivity : AppCompatActivity() {

    companion object{
        var  menuIds = ArrayList<String>()
        var total = 0.0
    }
    lateinit var adapter : CommandeAdapter
    var commandes = ArrayList<CommandeModel>()
    var allCommandes = ArrayList<CommandeModel>()
    private val viewModel: CommandesViewModel by viewModels()
    var userId = 0
    var mode=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commande)
        mode = Paper.book().read(MODE_WOK)
     //   userId = Paper.book().read<Int>(USER_ID)
        initUi()

        allCommandes.addAll(DetailsWokActivityStep1.commandes)
        allCommandes.addAll(DetailsWokActivityStep2.commandes)
        allCommandes.addAll(DetailsWokActivityStep3.commandes)
        if (! allCommandes.isNullOrEmpty()) {
            var wok = CommandeModel("", "", DetailsWokActivityStep1.basePrice, "ic_guest_wok", 1)
            for (com in allCommandes) {
                if (com.wok == true) {
                    wok.title += "\n ${com.title}"

                    wok.price = (wok.price.toDouble() + com.price.toDouble()).toString()
                } else
                    commandes.add(com)
            }
            var pr =  String.format("%.2f", wok.price.toDouble())
            wok.price = pr
            commandes.add(0, wok)
            adapter.setData(commandes)
            btn_next.setBackgroundResource(R.drawable.shape_top_corners_button)
            btn_next.isClickable = true
            updateValues("")
        }
        else
        {
            btn_next.setBackgroundResource(R.drawable.shape_top_corners_button_grey)
            btn_next.isClickable = false
        }
        setupNavigation()
    }

    private fun initUi() {
        adapter = CommandeAdapter(this,this::updateValues)
        recycler_commande.init(adapter, LinearLayoutManager.VERTICAL)

    }

   /* private fun onClickListnerItem(position: Int) {
        adapter.selectOrUnSelect(position)
    }*/

    private fun setupNavigation() {
       /* btn_back.setOnClickListener {
            onBackPressed()
        }
*/
        btn_next.setOnClickListener {
            menuIds = ArrayList<String>()
            for (item in allCommandes)
                menuIds.add(item.id)
            toPayment()
            /*for (cmd in allCommandes)
                Log.i("WOK_LIST"," ${cmd.id} , ${cmd.title}")*/

            //("Lyon France", "Lyon France", "FR", client_phone, "68100","Lyon",
            //                    userId, total.toString(), menuIds)

        }

    }



    fun updateValues(id : String){
        allCommandes.removeAll {
            it.id == id
        }

        total = 0.0
        for (item in adapter.getItems()){
            var priceWithoutComma = item.price.replace(",",".")
            var pr = priceWithoutComma.toDouble()
            var prix = String.format("%.2f", pr)
            total += prix.toDouble()
        }
        var price = String.format("%.2f", total)
        var tax = String.format("%.2f", total/5)
        txt_top.text = "${adapter.getItems().size} articles / Total panier : " + String.format("%.2f", total) + "€"
        txt_price.text = "$price€"
        txt_tax.text= "Dont TVA 20% : $tax€"
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


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, 0)
    }
}
