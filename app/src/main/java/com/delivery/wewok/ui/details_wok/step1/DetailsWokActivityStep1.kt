package com.delivery.wewok.ui.details_wok.step1

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.core.view.marginStart
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.delivery.wewok.R
import com.delivery.wewok.base.ext.init
import com.delivery.wewok.base.ext.setImage
import com.delivery.wewok.base.ext.toast
import com.delivery.wewok.data.model.CommandeModel
import com.delivery.wewok.ui.details_wok.commande.CommandeActivity
import com.delivery.wewok.ui.details_wok.step1.autre.AutreFragment
import com.delivery.wewok.ui.details_wok.step1.fromage.FromageFragment
import com.delivery.wewok.ui.details_wok.step1.fruits.FruitsFragment
import com.delivery.wewok.ui.details_wok.step1.proteines.ProteinesFragment
import com.delivery.wewok.ui.details_wok.step2.DetailsWokActivityStep2
import com.delivery.wewok.ui.details_wok.step3.DetailsWokActivityStep3
import com.delivery.wewok.ui.home.HomeActivity
import com.delivery.wewok.ui.home.home.HomeViewModel
import com.delivery.wewok.utils.CODE_ZONE
import com.delivery.wewok.utils.MODE_WOK
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_details_wok_step1.*
import kotlinx.android.synthetic.main.activity_details_wok_step1.btn_back
import kotlinx.android.synthetic.main.activity_details_wok_step1.btn_cart
import kotlinx.android.synthetic.main.activity_details_wok_step1.lbl_description
import kotlinx.android.synthetic.main.activity_details_wok_step1.lbl_price
import kotlinx.android.synthetic.main.activity_details_wok_step1.lbl_title
import kotlinx.android.synthetic.main.activity_details_wok_step1.roundedImageView
import kotlinx.android.synthetic.main.activity_details_wok_step3.*
import kotlinx.android.synthetic.main.activity_payment.*


@AndroidEntryPoint
class DetailsWokActivityStep1 : AppCompatActivity() {

    companion object{
        lateinit var layout_quantity : LinearLayout
        lateinit var img_quantity : AppCompatImageView
        lateinit var txt_granitur : TextView
        lateinit var txt_quantity : TextView
        var commandes = ArrayList<CommandeModel>()
        var selectedProtein: CommandeModel? = null
        var selectedFromage: CommandeModel? = null
        var selectedFruit: CommandeModel? = null
        var selectedAutre: CommandeModel? = null
        var basePrice : String ="0.0"

    }

    private val viewModel: DetailsWokActivityStep1ViewModel by viewModels()
    private val viewModelHome: HomeViewModel by viewModels()
    lateinit var adapterRecyclerViewVotreBase : BasesAdapter
    lateinit var adapterRecyclerViewRetirerIngredient : RetierIngredientsAdapter
    lateinit var adapterRecyclerViewToppings : ToppingsAdapter
    lateinit var pagerAdapter : PagerAdapter
    val sauces = HomeActivity.menu.sauces.get(0).items?.get(0)?.ingredientItems
    var sauce = false
    var base = false
    var codePostal =""
    lateinit var selectedSauce : CommandeModel
    var proteinFrag =ProteinesFragment.newInstance()
    var fromageFrag = FromageFragment.newInstance()
    var fruitsFrag= FruitsFragment.newInstance()
    var autreFrag= AutreFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_wok_step1)
        layout_quantity = findViewById<LinearLayout>(R.id.ly_quantite)
        img_quantity = findViewById<AppCompatImageView>(R.id.icon_graniture)
        txt_granitur = findViewById<TextView>(R.id.txt_graniture)
        txt_quantity = findViewById<TextView>(R.id.txt_quantite)

        recyclerview_retier_ingredient.setFocusable(false);
        ly_ly.requestFocus()
        /*var mode =  Paper.book().read<String>(MODE_WOK)
        if (mode.equals("MODE_LIVRAISON")) {
            if (Paper.book().contains(CODE_ZONE)) {
                codePostal = Paper.book().read(CODE_ZONE)
                if (!codePostal.isNullOrEmpty()) {
                    viewModelHome.checkZone(codePostal)
                }
            }
        }*/



       HomeActivity.model.price.let {
           basePrice = it.toString();
       }
       // txt_graniture
        icon_graniture
        initUi()
        Log.i("SG_B :", sauce_group.size.toString())
        getExtras()
        bindWokInformations()
        setUpNavigation()

        Log.i("SG_A :", sauce_group.size.toString())
        sauce_group.setOnCheckedChangeListener { group, checkedId ->
            var id =  checkedId
            sauce = true
            checkContinue()
            if (checkedId > sauce_group.size){
                id = (checkedId % sauce_group.size)-1
                Log.i("CHECKEID", " : " + id.toString())
                if (id == -1){
                    id =  sauce_group.size-1
                }
              //  Log.i("CHECKEID", " Size : " + sauce_group.size)
                var sauce = sauces?.get(id)

                Log.i("CHECKEID", " : "+sauce?.name)
                selectedSauce = CommandeModel(sauce?.id!!,sauce?.name!!,sauce?.price!!,sauce?.image,1,true)
            }
            else {
                Log.i("CHECKEID", " ch : " + (checkedId - 1).toString())
                Log.i("CHECKEID", " ch Size : " + sauce_group.size)
                var sauce = sauces?.get(checkedId - 1)
                Log.i("CHECKEID", " ch : "+sauce?.name)


                Log.i("All_Commandes", "sauce $sauce")
                selectedSauce = CommandeModel(sauce?.id!!, sauce?.name!!, sauce?.price!!, sauce?.image, 1, true)
            }
        }

        btn_cart.setOnClickListener {
            toCommandes()
        }
    }

    fun toCommandes(){
        startActivity(Intent(this, CommandeActivity::class.java))
    }
    private fun getExtras()
    {
        val bases = HomeActivity.model.items?.get(0)?.ingredientItems
        adapterRecyclerViewVotreBase.setData(bases!!)
        val retierIngrediants = HomeActivity.model.items?.get(1)?.ingredientItems
        adapterRecyclerViewRetirerIngredient.setData(retierIngrediants!!)
        val toppings = HomeActivity.menu.toppings
        adapterRecyclerViewToppings.setData(toppings!!)

        val myColorStateList = ColorStateList(arrayOf(intArrayOf(resources.getColor(R.color.white))), intArrayOf(resources.getColor(R.color.orange)))
      //  sauce_group.removeAllViews()
        if (sauce_group.isEmpty()) {
            if (sauces != null) {
                for (radio in sauces) {
                    var rb = RadioButton(this)
                    rb.text = radio.name

                    // rb.layoutParams = RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, 35)
                    rb.setPadding(0, 20, 0, 20)
                    rb.buttonTintList = myColorStateList
                    sauce_group.addView(rb)
                }
            }
        }
      // (radios)
    }

    private fun bindWokInformations()
    {

            val wok = HomeActivity.model
            lbl_title.text = wok.title
            lbl_description.text = wok.description
            roundedImageView.setImage(wok.image,wok.title!!)
            lbl_price.text = "${String.format("%.2f", wok.price!!.toFloat())}€"

    }

    private fun initUi()
    {
        initRecyclerviews()
        initViewPager()
    }

    private fun initRecyclerviews()
    {
        adapterRecyclerViewVotreBase = BasesAdapter(this,this::onClickListnerBasesItem)
        recyclerview_votre_base.init(adapterRecyclerViewVotreBase,LinearLayoutManager.HORIZONTAL)
        adapterRecyclerViewRetirerIngredient = RetierIngredientsAdapter(this,this::onClickListnerRetierIngredientItem)
        recyclerview_retier_ingredient.init(adapterRecyclerViewRetirerIngredient,LinearLayoutManager.HORIZONTAL)
        adapterRecyclerViewToppings =  ToppingsAdapter(this,this::onClickListnerToppingstItem)
        recyclerview_toppings.init(adapterRecyclerViewToppings,LinearLayoutManager.HORIZONTAL)
    }

    private fun initViewPager()
    {
        pagerAdapter = PagerAdapter(supportFragmentManager)

        pagerAdapter.addFragment(proteinFrag,ProteinesFragment.getName(this))
        pagerAdapter.addFragment(fromageFrag,FromageFragment.getName(this))
        pagerAdapter.addFragment(fruitsFrag,FruitsFragment.getName(this))
        pagerAdapter.addFragment(autreFrag,AutreFragment.getName(this))
        view_pager.adapter = pagerAdapter
        view_pager.offscreenPageLimit = pagerAdapter.count
        tab_layout.setupWithViewPager(view_pager)
    }

    private fun onClickListnerBasesItem(position:Int)
    {
        adapterRecyclerViewVotreBase.selectOrUnSelect(position)
        base = true
        checkContinue()
    }

    private fun onClickListnerRetierIngredientItem(position:Int)
    {
        adapterRecyclerViewRetirerIngredient.selectOrUnSelect(position)
       // checkContinue()
    }

    private fun onClickListnerToppingstItem(position:Int)
    {
        adapterRecyclerViewToppings.selectOrUnSelect(position)

    }


    fun checkContinue()
    {
        if (base && sauce){
            btn_next.setBackgroundResource(R.drawable.shape_top_corners_button)
            btn_next.isClickable = true
        }
        else {
            btn_next.setBackgroundResource(R.drawable.shape_top_corners_button_grey)
            btn_next.isClickable = false
        }
    }

    private fun setUpNavigation()
    {
        btn_next.setOnClickListener {
           // adapterRecyclerViewVotreBase : BasesAdapter
           // lateinit var adapterRecyclerViewRetirerIngredient : RetierIngredientsAdapter
           // lateinit var adapterRecyclerViewToppings : ToppingsAdapter
      //      var wok = CommandeModel()

            commandes.clear()
            commandes.add(adapterRecyclerViewVotreBase.getSelectedItems())
            if ( ! adapterRecyclerViewRetirerIngredient.getSelectedItems().isNullOrEmpty())
                commandes.addAll(adapterRecyclerViewRetirerIngredient.getSelectedItems())
            if ( ! adapterRecyclerViewToppings.getSelectedItems().isNullOrEmpty())
                commandes.addAll(adapterRecyclerViewToppings.getSelectedItems())
            if ( selectedFromage != null)
                commandes.add(selectedFromage!!)
            if ( selectedFruit != null)
                commandes.add(selectedFruit!!)
            if ( selectedProtein != null)
                commandes.add(selectedProtein!!)
            if ( selectedAutre != null)
                commandes.add(selectedAutre!!)
            commandes.add(selectedSauce)
            val intent = Intent(this, DetailsWokActivityStep2::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
        btn_back.setOnClickListener {
          /*  adapterRecyclerViewVotreBase.unselectAll()
            adapterRecyclerViewRetirerIngredient .unselectAll()
            adapterRecyclerViewToppings.unselectAll()
            proteinFrag.unselectAll()*/
            //finish()
            onBackPressed()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        adapterRecyclerViewVotreBase.unselectAll()
        adapterRecyclerViewRetirerIngredient .unselectAll()
        adapterRecyclerViewToppings.unselectAll()
        proteinFrag.unselectAll()
        fruitsFrag.unselectAll()
        autreFrag.unselectAll()
        fruitsFrag.unselectAll()
        finish()
    }

    private fun sendOnStartEvent()
    {
        viewModel.setStateEvent(DetailsWokActivityStep1ViewModel.StateEvent.OnStart)
    }
}