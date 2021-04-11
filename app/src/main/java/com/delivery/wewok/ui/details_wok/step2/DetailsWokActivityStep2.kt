package com.delivery.wewok.ui.details_wok.step2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.delivery.wewok.R
import com.delivery.wewok.base.ext.init
import com.delivery.wewok.data.model.CommandeModel
import com.delivery.wewok.ui.details_wok.commande.CommandeActivity
import com.delivery.wewok.ui.details_wok.step1.DetailsWokActivityStep1
import com.delivery.wewok.ui.details_wok.step3.DetailsWokActivityStep3
import com.delivery.wewok.ui.home.HomeActivity
import com.mobilemovement.kotlintvmaze.base.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details_wok_step2.*
import kotlinx.android.synthetic.main.activity_details_wok_step2.btn_add
import kotlinx.android.synthetic.main.activity_details_wok_step2.btn_back
import kotlinx.android.synthetic.main.activity_details_wok_step2.btn_cart
import kotlinx.android.synthetic.main.activity_details_wok_step2.btn_not_need
import kotlinx.android.synthetic.main.activity_details_wok_step3.*

@AndroidEntryPoint
class DetailsWokActivityStep2 : AppCompatActivity() {

    companion object{
        var commandes = ArrayList<CommandeModel>()
    }


    private val viewModel: DetailsWokActivityStep2ViewModel by viewModels()
    lateinit var adapter : BoissonsAdapter
    var listBoisson = ArrayList<CommandeModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_wok_step2)
        viewModel.getBoissons()
        initUi()
        adapter.setData(HomeActivity.menu.boissons)
        setupNavigation()
    }

    private fun initUi()
    {
        adapter = BoissonsAdapter(this,this::onClickListnerItem)
        recycler_view_nos_boissons.init(adapter,LinearLayoutManager.VERTICAL)
    }

    private fun onClickListnerItem(position:Int){
        var boisson = adapter.selectOrUnSelect(position)
        if (boisson.selected)
            listBoisson.add(CommandeModel(boisson.id!!,boisson.title!!,boisson.price!!,boisson.image,1))
        else
            listBoisson.removeAll {
                it.id == boisson.id
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
            goToStep3()
        }
        btn_add.setOnClickListener {
            addCommandes()
            goToStep3()
        }
        btn_cart.setOnClickListener {
            toCommandes()
        }
    }

    fun addCommandes(){
        commandes.clear()
       commandes.addAll(listBoisson)
    }

    fun removeCommandes(){
        adapter.unselectAll()
        commandes.clear()
    }

    fun toCommandes(){
        startActivity(Intent(this, CommandeActivity::class.java))
    }

    private fun goToStep3()
    {
        val intent = Intent(this, DetailsWokActivityStep3::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        adapter.unselectAll()
        overridePendingTransition(0,0)
    }


}