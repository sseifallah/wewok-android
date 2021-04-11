package com.delivery.wewok.ui.details_wok.step3

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.delivery.wewok.R
import com.delivery.wewok.base.ext.init
import com.delivery.wewok.data.model.CommandeModel
import com.delivery.wewok.ui.details_wok.commande.CommandeActivity
import com.delivery.wewok.ui.home.HomeActivity
import com.delivery.wewok.ui.utils.CustomDialogClass
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details_wok_step3.*
import kotlinx.android.synthetic.main.activity_details_wok_step3.btn_add
import kotlinx.android.synthetic.main.activity_details_wok_step3.btn_back
import kotlinx.android.synthetic.main.activity_details_wok_step3.btn_cart
import kotlinx.android.synthetic.main.activity_details_wok_step3.btn_not_need

@AndroidEntryPoint
class DetailsWokActivityStep3 : AppCompatActivity() {

    companion object{
        var commandes = ArrayList<CommandeModel>()
    }

    private val viewModel: DetailsWokActivityStep3ViewModel by viewModels()
    lateinit var adapter : DessertsAdapter
    var listDesserts = ArrayList<CommandeModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_wok_step3)
        viewModel.getDesserts()
        initUi()
        adapter.setData(HomeActivity.menu.desserts)
        setupNavigation()
    }

    private fun initUi()
    {
        adapter = DessertsAdapter(this,this::onClickListnerItem)
        recycler_view_nos_desserts.init(adapter,LinearLayoutManager.VERTICAL)
    }

    private fun onClickListnerItem(position:Int){
        var dessert = adapter.selectOrUnSelect(position)
        if (dessert.selected)
            listDesserts.add(CommandeModel(dessert.id!!,dessert.title!!,dessert.price!!,dessert.image,1))
        else
            listDesserts.removeAll {
                it.id == dessert.id
            }
    }

    private fun setupNavigation()
    {
        btn_back.setOnClickListener {
            onBackPressed()
        }
        btn_cart.setOnClickListener {

        }
        btn_not_need.setOnClickListener {
            removeCommandes()
            showConfirmDialog()
        }
        btn_add.setOnClickListener {
            addCommandes()
            showConfirmDialog()
        }
        btn_cart.setOnClickListener {
            toCommandes()
        }
    }

    fun showConfirmDialog(){
        val cdd = CustomDialogClass(this,true)
        cdd.getWindow()?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));
        cdd.setCanceledOnTouchOutside(false)
        cdd.show()
        cdd.bn_oui.setOnClickListener {
            startActivity(Intent(this, CommandeActivity::class.java))
            cdd.dismiss()
        }
        cdd.bn_retour.setOnClickListener {
            cdd.dismiss()
        }
    }

    fun toCommandes(){
        startActivity(Intent(this, CommandeActivity::class.java))
    }

    fun addCommandes(){
        commandes.clear()
        commandes.addAll(listDesserts)
    }

    fun removeCommandes(){
        adapter.unselectAll()
        commandes.clear()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0,0)
        adapter.unselectAll()
    }

}