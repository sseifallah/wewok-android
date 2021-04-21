package fr.mi.wewok.ui.details_wok.step2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import fr.mi.wewok.R
import fr.mi.wewok.base.ext.init
import fr.mi.wewok.data.model.CommandeModel
import fr.mi.wewok.ui.details_wok.commande.CommandeActivity
import fr.mi.wewok.ui.details_wok.step3.DetailsWokActivityStep3
import fr.mi.wewok.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details_wok_step2.*
import kotlinx.android.synthetic.main.activity_details_wok_step2.btn_add
import kotlinx.android.synthetic.main.activity_details_wok_step2.btn_back
import kotlinx.android.synthetic.main.activity_details_wok_step2.btn_cart
import kotlinx.android.synthetic.main.activity_details_wok_step2.btn_not_need

@AndroidEntryPoint
class DetailsWokActivityStep2 : AppCompatActivity() {

    companion object{
        var commandes = ArrayList<CommandeModel>()
        lateinit var adapter : BoissonsAdapter
    }


    private val viewModel: DetailsWokActivityStep2ViewModel by viewModels()
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