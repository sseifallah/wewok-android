package fr.mi.wewok.ui.details_wok.step1

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
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import fr.mi.wewok.R
import fr.mi.wewok.base.ext.init
import fr.mi.wewok.base.ext.setImage
import fr.mi.wewok.data.model.CommandeModel
import fr.mi.wewok.ui.details_wok.commande.CommandeActivity
import fr.mi.wewok.ui.details_wok.step1.autre.AutreFragment
import fr.mi.wewok.ui.details_wok.step1.fromage.FromageFragment
import fr.mi.wewok.ui.details_wok.step1.fruits.FruitsFragment
import fr.mi.wewok.ui.details_wok.step1.proteines.ProteinesFragment
import fr.mi.wewok.ui.details_wok.step2.DetailsWokActivityStep2
import fr.mi.wewok.ui.home.HomeActivity
import fr.mi.wewok.ui.home.home.HomeViewModel
import kotlinx.android.synthetic.main.activity_details_wok_step1.*


@AndroidEntryPoint
class DetailsWokActivityStep1 : AppCompatActivity() , CompoundButton.OnCheckedChangeListener{

    companion object{

        lateinit var layout_quantity : LinearLayout
        lateinit var img_quantity : AppCompatImageView
        lateinit var txt_granitur : TextView
        lateinit var txt_quantity : TextView
        lateinit var btn_qnt_plus : AppCompatImageView
        lateinit var btn_qnt_minus : AppCompatImageView
        lateinit var adapterRecyclerViewQuantite : QuantiteAdapter
        var commandes = ArrayList<CommandeModel>()
        var selectedProtein = ArrayList<CommandeModel>()
        var selectedFromage = ArrayList<CommandeModel>()
        var selectedFruit = ArrayList<CommandeModel>()
        var selectedAutre = ArrayList<CommandeModel>()
        var basePrice : String ="0.0"
        lateinit var proteinFrag :ProteinesFragment
        lateinit var fromageFrag : FromageFragment
        lateinit var fruitsFrag : FruitsFragment
        lateinit var autreFrag : AutreFragment
        lateinit var adapterRecyclerViewVotreBase : BasesAdapter
        lateinit var adapterRecyclerViewRetirerIngredient : RetierIngredientsAdapter
        lateinit var adapterRecyclerViewToppings : ToppingsAdapter
    }

    private val viewModel: DetailsWokActivityStep1ViewModel by viewModels()
    private val viewModelHome: HomeViewModel by viewModels()

    lateinit var pagerAdapter : PagerAdapter
    val sauces = HomeActivity.menu.sauces.get(0).items?.get(0)?.ingredientItems
    val couverts = HomeActivity.menu.couverts.get(0).items?.get(0)?.ingredientItems
    var sauce = false
    var base = false
    var codePostal =""
    lateinit var selectedSauce : CommandeModel
    var selectedCouv = ArrayList<CommandeModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        proteinFrag =ProteinesFragment.newInstance()
        fromageFrag = FromageFragment.newInstance()
        fruitsFrag= FruitsFragment.newInstance()
        autreFrag= AutreFragment.newInstance()

        setContentView(R.layout.activity_details_wok_step1)
        layout_quantity = findViewById<LinearLayout>(R.id.ly_quantite)
        /*img_quantity = findViewById<AppCompatImageView>(R.id.icon_graniture)
        txt_granitur = findViewById<TextView>(R.id.txt_graniture)
        txt_quantity = findViewById<TextView>(R.id.txt_quantite)
        btn_qnt_plus = findViewById<AppCompatImageView>(R.id.btn_plus)
        btn_qnt_minus = findViewById<AppCompatImageView>(R.id.btn_minus)
        //Paper.book().delete(SAVED_ORDERS)*/
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
        //icon_graniture
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

        if (couverts_group.isEmpty()) {
            var i = 0
            if (couverts != null) {
                for (radio in couverts) {
                    val row = TableRow(this)
                    row.id = i
                    row.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
                    val checkBox = CheckBox(this)
                    checkBox.setPadding(0, 20, 0, 20)
                    checkBox.id = i
                    checkBox.buttonTintList = myColorStateList
                    checkBox.setText(radio.name)
                    checkBox.setOnCheckedChangeListener(this)
                    row.addView(checkBox)
                    couv_ll.addView(row)

                    i++
                    /*var boxx = CheckBox(this)
                    boxx.text = radio.name+"RG"
                    boxx.setOnCheckedChangeListener { buttonView, isChecked ->
                        Log.i("CHECK_BOX", "checked $i")
                    }
                    boxx.setPadding(0, 20, 0, 20)
                    boxx.buttonTintList = myColorStateList
                    couverts_group.addView(boxx)
                    i++*/
                   /* var rb = RadioButton(this)
                    rb.text = radio.name

                    // rb.layoutParams = RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, 35)
                    rb.setPadding(0, 20, 0, 20)
                    rb.buttonTintList = myColorStateList
                    couv_ll.addView(rb)*/
                   /* var box = CheckBox(this)
                    box.text = radio.name
                    box.setOnCheckedChangeListener { buttonView, isChecked ->
                        Log.i("CHECK_BOX", "checked")
                    }
                    box.setPadding(0, 20, 0, 20)
                    box.buttonTintList = myColorStateList
                    couv_ll.addView(box)*/
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
        adapterRecyclerViewQuantite = QuantiteAdapter(this, this::setQuantiteGone,this::unselectIngredient)
        qnt_recycler.init(adapterRecyclerViewQuantite,LinearLayoutManager.VERTICAL)
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

    fun setQuantiteGone(){
        layout_quantity.visibility = View.GONE
    }

    fun unselectIngredient(id :String , type : Int){
        when(type){
            1 -> proteinFrag.unselectProteine(id)
            2 -> fromageFrag.unselectFromage(id)
            3 -> fruitsFrag.unselectFruit(id)
            4 -> autreFrag.unselectAutre(id)
        }

    }

    fun checkContinue()
    {
        if (base && sauce){
            btn_next.setBackgroundResource(R.drawable.shape_top_corners_button)
            btn_next.isClickable = true
            btn_next.isEnabled = true
        }
        else {
            btn_next.setBackgroundResource(R.drawable.shape_top_corners_button_grey)
            btn_next.isClickable = false
            btn_next.isEnabled = false
        }
    }

    private fun setUpNavigation()
    {
        btn_next.setOnClickListener {
           // adapterRecyclerViewVotreBase : BasesAdapter
           // lateinit var adapterRecyclerViewRetirerIngredient : RetierIngredientsAdapter
           // lateinit var adapterRecyclerViewToppings : ToppingsAdapter
      //      var wok = CommandeModel()
            var wokCommande = ArrayList<CommandeModel>()
            commandes.clear()

            wokCommande.add(adapterRecyclerViewVotreBase.getSelectedItems())
            wokCommande.add(selectedSauce)
            if ( ! adapterRecyclerViewRetirerIngredient.getSelectedItems().isNullOrEmpty())
                wokCommande.addAll(adapterRecyclerViewRetirerIngredient.getSelectedItems())
            if (! selectedCouv.isNullOrEmpty())
                wokCommande.addAll(selectedCouv)

            if ( ! adapterRecyclerViewToppings.getSelectedItems().isNullOrEmpty())
                wokCommande.addAll(adapterRecyclerViewToppings.getSelectedItems())
            /*if ( selectedFromage.size != 0)
                wokCommande.addAll(selectedFromage)*/
            if ( adapterRecyclerViewQuantite.itemCount!= 0)
                wokCommande.addAll(adapterRecyclerViewQuantite.getAllItems()!!)


       //     if (selecte)
          //  wokCommande.add(selectedCouv)

            var wok = CommandeModel("", "",basePrice, "ic_guest_wok", 1,true)
            for (com in wokCommande) {
                if (com.wok == true) {
                    wok.id+= com.id+","
                    if (com.qunatity == 1)
                        wok.title += "${com.title}\n"
                    else
                        wok.title += "${com.qunatity}x ${com.title}\n"
                    var priceWithoutComma = wok.price.replace(",",".")
                    var compriceWithoutComma = com.price.replace(",",".")
                    wok.price = (priceWithoutComma.toDouble() + (compriceWithoutComma.toDouble() * com.qunatity) ).toString()
                }
                else
                    commandes.add(com)
            }
            wok.title = wok.title.substring(0,wok.title.length-2)
            var priceWithoutComma = wok.price.replace(",",".")
            var pr =  String.format("%.2f", priceWithoutComma.toDouble())
            wok.price = pr
            wok.id= wok.id.subSequence(0,wok.id.length-1) as String
            Log.i("CMD_ID"," : wok id  ${wok.id} ")
            commandes.add(0, wok)
           // commandes.add(selectedSauce)
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


    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        Log.i("CHECKED_ID",buttonView?.id.toString())
        if (isChecked) {
            var couv = couverts?.get(buttonView!!.id)
            selectedCouv.add(CommandeModel(couv?.id!!, couv?.name!!, couv?.price!!, couv?.image, 1, true))
            Log.i("CHECKED_ID"," Added : ${couv.name} ${couv.id} ")
        }
        else {
            var couv = couverts?.get(buttonView!!.id)
            selectedCouv.removeAll {
                it.id == couv?.id
            }
            Log.i("CHECKED_ID"," Removed  : ${couv?.name} ${couv?.id} ")
        }
    }
}