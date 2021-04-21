package com.delivery.wewok.ui.details_wok.payement

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.delivery.wewok.R
import com.delivery.wewok.base.ext.log
import com.delivery.wewok.base.ext.toast
import com.delivery.wewok.data.model.GetPaymentUrl
import com.delivery.wewok.data.model.SaveResponse
import com.delivery.wewok.data.model.ZoneModel
import com.delivery.wewok.ui.details_wok.SuccessActivity
import com.delivery.wewok.ui.details_wok.commande.CommandeActivity
import com.delivery.wewok.ui.details_wok.commande.CommandesViewModel
import com.delivery.wewok.ui.details_wok.step1.DetailsWokActivityStep1
import com.delivery.wewok.ui.details_wok.step2.DetailsWokActivityStep2
import com.delivery.wewok.ui.home.home.HomeViewModel
import com.delivery.wewok.ui.home.profil.ProfilEditFragment
import com.delivery.wewok.ui.home.profil.ProfilFragment
import com.delivery.wewok.utils.*
import com.mobilemovement.kotlintvmaze.base.Resource
import com.mobilemovement.kotlintvmaze.base.Status
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_commande.*
import kotlinx.android.synthetic.main.activity_details_wok_step1.*
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.activity_payment_livraison.payment_livraison_fragment_container
import kotlinx.android.synthetic.main.activity_payment.pg_payment
import kotlinx.android.synthetic.main.activity_payment.radio_carte
import kotlinx.android.synthetic.main.activity_payment_livraison.*
import kotlinx.android.synthetic.main.fragment_profil.*

@AndroidEntryPoint
class PaymentLivraisonActivity : AppCompatActivity() {
    companion object{
        var orderId = 0
    }
    var frais =""
   // var orderId = 0
    var zone_ville_updated = false
    var zoneChecked = false
    lateinit var currentFragment : Fragment
    private val fragmentManager = supportFragmentManager
    private val viewModel: CommandesViewModel by viewModels()
    private val viewModelHome: HomeViewModel by viewModels()
    var client_adress = " "
    var client_city = ""
    var client_country = "FR"
    var client_phone = " "
    var client_postcode  = ""
    var id = 0
    var total = 0.0
    var  menuIds = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_livraison)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            getWindow().getDecorView().setImportantForAutofill(
                View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS);
        }
        btn_next_payment_liv.isClickable = false
        btn_next_payment_liv.isEnabled = false
        menuIds.clear()
        menuIds.addAll(CommandeActivity.menuIds)
        total = CommandeActivity.total
        updateInformations(false)
        for (com in DetailsWokActivityStep1.commandes)
            Log.i("All_Commandes"," ${com.title} ")

        radio_livraison.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                radio_carte.isChecked = false
                activeNext()
            }
        }
        radio_carte.setOnClickListener {
            radio_livraison.isChecked = false
            //ly_carte.visibility = View.VISIBLE
            activeNext()
        }

        btn_next_payment_liv.setOnClickListener {
            if (radio_carte.isChecked){
                if (zone_ville_updated) {
                    if (zoneChecked) {
                        var priceWithoutComma = frais.substring(0,frais.length-2).replace(",",".")
                        var totalCmd = total+ priceWithoutComma.toDouble()
                        viewModel.saveOrder(client_adress, client_adress, client_country, client_phone, client_postcode, client_adress,
                            id, totalCmd.toString(), menuIds)
                        //("Lyon France", "Lyon France", "FR", client_phone, "68100","Lyon",
                        //                    id, total.toString(), menuIds)
                        pg_payment.visibility = View.VISIBLE
                        viewModel.saveLiveData.observe(this, saveWebObserver)
                    }
                    else
                        toast("Votre région n'est pas encore couverte pour la livraison.")
                }
                else {
                    toast("Veuillez remplir toutes vos informations personnelles avant de passer une commande")
                    currentFragment = ProfilEditFragment(true)
                    fragmentManager.beginTransaction()
                            .replace(R.id.payment_livraison_fragment_container, currentFragment)
                            .addToBackStack(null)
                            .commit()
                    payment_livraison_fragment_container.visibility = View.VISIBLE
                }

            }
            else if (radio_livraison.isChecked){
                if (zone_ville_updated) {
                    if (zoneChecked) {
                        var priceWithoutComma = frais.substring(0,frais.length-2).replace(",",".")
                        var totalCmd = total+ priceWithoutComma.toDouble()
                        viewModel.saveOrder(client_adress, client_adress, client_country, client_phone, client_postcode,client_adress,
                                id, totalCmd.toString(), menuIds)
                        //("Lyon France", "Lyon France", "FR", client_phone, "68100","Lyon",
                        //                    id, total.toString(), menuIds)
                        pg_payment.visibility = View.VISIBLE
                        viewModel.saveLiveData.observe(this, saveObserver)

                    }
                    else
                        toast("Votre région n'est pas encore couverte pour la livraison.")
                }
                else {
                    toast("Veuillez remplir toutes vos informations personnelles avant de passer une commande")
                    currentFragment = ProfilEditFragment(true)
                    fragmentManager.beginTransaction()
                            .replace(R.id.payment_livraison_fragment_container, currentFragment)
                            .addToBackStack(null)
                            .commit()
                    payment_livraison_fragment_container.visibility = View.VISIBLE
                }
            }
        }

    }



    fun updateInformations( checked : Boolean){
        if (Paper.book().contains(USER_ADR)) {
            client_adress = Paper.book().read<String>(USER_ADR)
        }
        if (Paper.book().contains(USER_VILLE)) {
            client_city = Paper.book().read<String>(USER_VILLE)
        }

        if (Paper.book().contains(USER_NUM)){
            client_phone = Paper.book().read<String>(USER_NUM)
        }
        if (Paper.book().contains(CODE_ZONE)){
            client_postcode = Paper.book().read<String>(CODE_ZONE)
        }
        id = Paper.book().read<Int>(IID)
        Log.i("PAYMENT_ID"," : $id")
     //   var code =  Paper.book().read<String>(CODE_ZONE)
        Log.i("PAYMENT_CODE : "," $client_postcode")
        zone_ville_updated = checked
        //Log.i("PAYMENT_CODE : ",zone_ville_updated.toString())
        if (Paper.book().contains(ZONE_CHECKED))
            zoneChecked = Paper.book().read(ZONE_CHECKED)

        if (Paper.book().contains(FRAIS_LIVRAISON)){
            frais = Paper.book().read(FRAIS_LIVRAISON)
            if (! frais.isNullOrEmpty()) {
                txt_frais3.text = "Frais de livraison : $frais"
                txt_frais1.text = "Frais de livraison : $frais"
                txt_frais2.text = "Frais : $frais"
            }
        }
    }

    var saveObserver: Observer<Resource<SaveResponse>> =
            object : Observer<Resource<SaveResponse>> {
                override fun onChanged(it: Resource<SaveResponse>?) {
                    pg_payment.visibility = View.INVISIBLE
                    when (it!!.status){
                        Status.SUCCESS ->{
                            Log.i("SAVE_RESP","SUCCESS")
                            it.data?.order_id.let {
                                orderId =it!!
                                Paper.book().write(ORDER_ID,it)

                                val intent = Intent(this@PaymentLivraisonActivity, SuccessActivity::class.java)
                                startActivity(intent)
                            }
                            // Toast.makeText(baseContext, "SUCCESS", Toast.LENGTH_SHORT).show()

                        }
                        Status.NO_INTERNET ->{
                            pg_payment.visibility = View.INVISIBLE
                            Log.i("SAVE_RESP","NO_INTERNET")
                            Toast.makeText(baseContext, it.message, Toast.LENGTH_SHORT).show()
                        }
                        else->{
                            pg_payment.visibility = View.INVISIBLE
                            Log.i("SAVE_RESP","ELSE")
                            Toast.makeText(baseContext, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }


    var saveWebObserver: Observer<Resource<SaveResponse>> =
            object : Observer<Resource<SaveResponse>> {
                override fun onChanged(it: Resource<SaveResponse>?) {

                    when (it!!.status){
                        Status.SUCCESS ->{
                            Log.i("SAVE_RESP","Succès")
                            it.data?.order_id.let {
                                orderId =it!!
                                Paper.book().write(ORDER_ID,it)
                                viewModel.getPaymentUrl(orderId)
                                pg_payment.visibility = View.VISIBLE
                                viewModel.urlLiveData.observe(this@PaymentLivraisonActivity, urlObserver)
                            }
                            // Toast.makeText(baseContext, "SUCCESS", Toast.LENGTH_SHORT).show()

                        }
                        Status.NO_INTERNET ->{
                            pg_payment.visibility = View.INVISIBLE
                            Log.i("SAVE_RESP","NO_INTERNET")
                            Toast.makeText(baseContext, it.message, Toast.LENGTH_SHORT).show()
                        }
                        else->{
                            pg_payment.visibility = View.INVISIBLE
                            Log.i("SAVE_RESP","ELSE")
                            Toast.makeText(baseContext, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

    fun setFragmentVisibility(checked: Boolean){
        Log.i("PAYMENT_UPDATE"," : UPDATED")
        Log.i("PROFILE_EDITED PAY"," : $checked")
        payment_livraison_fragment_container.visibility = View.INVISIBLE
        updateInformations(checked)
    }


    var zoneObserver: Observer<Resource<ZoneModel>> =
            object : Observer<Resource<ZoneModel>> {
                override fun onChanged(it: Resource<ZoneModel>?) {
                    pg_payment.visibility = View.INVISIBLE
                    Log.i("ZONE_REQ Result","$it")
                    when (it!!.status){
                        Status.SUCCESS ->{
                            Log.i("ZONE_REQ","SUCCESS")
                            //Toast.makeText(requireContext(), "SUCCESS", Toast.LENGTH_SHORT).show()
                        }
                        Status.NO_INTERNET ->{
                            Log.i("ZONE_REQ","NO_INTERNET")
                            Toast.makeText(this@PaymentLivraisonActivity, it.message, Toast.LENGTH_SHORT).show()
                        }
                        else->{
                            Log.i("ZONE_REQ","ELSE")
                            Toast.makeText(this@PaymentLivraisonActivity, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

    var urlObserver: Observer<Resource<GetPaymentUrl>> =
            object : Observer<Resource<GetPaymentUrl>> {
                override fun onChanged(it: Resource<GetPaymentUrl>?) {
                    pg_payment.visibility = View.INVISIBLE
                    when (it!!.status){
                        Status.SUCCESS ->{

                            Log.i("URL_RESP","SUCCESS")
                            it.data.let {
                               toWebView(it?.pay_url,it?.success_url,it?.error_url)
                            }
                            // Toast.makeText(baseContext, "SUCCESS", Toast.LENGTH_SHORT).show()
                        }
                        Status.NO_INTERNET ->{
                            Log.i("SAVE_RESP","NO_INTERNET")
                            Toast.makeText(baseContext, it.message, Toast.LENGTH_SHORT).show()
                        }
                        else->{
                            Log.i("SAVE_RESP","ELSE")
                            Toast.makeText(baseContext, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

    fun activeNext()
    {
        btn_next_payment_liv.setBackgroundResource(R.drawable.shape_top_corners_button)
        btn_next_payment_liv.isClickable = true
        btn_next_payment_liv.isEnabled = true
    }

    fun toWebView(url  :String?,success_url  :String?,error_url  :String?,){
        val intent = Intent(baseContext, WebActivity::class.java)

        intent.putExtra(ORDER_ID,orderId)
        intent.putExtra("URL",url)
        intent.putExtra("Success_URL",success_url)
        intent.putExtra("Error_URL",error_url)
        startActivity(intent)
    }



}