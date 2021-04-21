package fr.mi.wewok.ui.details_wok.step1.proteines

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import fr.mi.wewok.R
import fr.mi.wewok.base.ext.init
import fr.mi.wewok.data.model.CommandeModel
import fr.mi.wewok.ui.details_wok.step1.DetailsWokActivityStep1
import fr.mi.wewok.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_proteine.*

@AndroidEntryPoint
class ProteinesFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = ProteinesFragment()

        @JvmStatic
        fun getName(context: Context) = context.getString(R.string.Protéines)

        @JvmStatic
        lateinit var adapter:ProteinesAdapter
    }

    lateinit var proteine_id : String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_proteine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        adapter.setData(HomeActivity.menu.mesGarnitures)
        //observeViewModel()
    }

    private fun initUi()
    {
        adapter = ProteinesAdapter(requireContext(),this::onClickItem)
        recyclerview.init(adapter, LinearLayoutManager.HORIZONTAL)
    }

    private fun onClickItem(position:Int)
    {
        var protein = adapter.selectOrUnSelect(position)
        proteine_id = protein.id!!
        Log.i("PROTEIN_ID","$proteine_id")
        Log.i("PROTEINS_SELECTED" , protein.title.toString() + " " +protein.selected.toString())
        if (protein.selected == true) {
            DetailsWokActivityStep1.layout_quantity.visibility = View.VISIBLE
            //DetailsWokActivityStep1.selectedProtein.add(CommandeModel(protein.id!!,protein.title!!,protein.price!!,protein.image,1,true))
            DetailsWokActivityStep1.adapterRecyclerViewQuantite.addData(CommandeModel(protein.id!!,protein.title!!,protein.price!!,protein.image,1,false,1))
            /*if (!protein.items.isNullOrEmpty()) {
                if (!protein.items!![0].ingredientItems.isNullOrEmpty()) {
                    DetailsWokActivityStep1.selectedProtein.find {
                        it.id.contains(protein.id!!,false)
                    }?.let {
                        it.wok = false
                    }
                    if (DetailsWokActivityStep1.layout_quantity.visibility == View.GONE) {
                        var items = protein.items!![0].ingredientItems
                        DetailsWokActivityStep1.layout_quantity.visibility = View.VISIBLE
                        DetailsWokActivityStep1.img_quantity.setImage(protein.image, "")
                        DetailsWokActivityStep1.txt_granitur.text = protein.title
                        var i = 0;
                        DetailsWokActivityStep1.txt_quantity.text =items!![0].name
                        DetailsWokActivityStep1.btn_qnt_plus.setOnClickListener {
                            if (items.size> i+1){
                                i++
                                DetailsWokActivityStep1.txt_quantity.text =items[i].name
                                DetailsWokActivityStep1.selectedProtein.find {
                                    it.id.contains(protein.id!!,false)
                                }?.let {
                                    it.id = items[i].id.toString()
                                    it.price = items[i].price.toString()
                                    it.qunatity = items[i].name!!.toInt()
                                    //it.title = items[i].name + "x "+ protein.title
                                }
                            }
                        }
                        DetailsWokActivityStep1.btn_qnt_minus.setOnClickListener {
                            if (i!=0){
                                i--
                                DetailsWokActivityStep1.txt_quantity.text =items!![i].name
                                DetailsWokActivityStep1.selectedProtein.find {
                                    it.id.contains(protein.id!!,false)
                                }?.let {
                                    it.id = items[i].id.toString()
                                    it.price = items[i].price.toString()
                                    it.qunatity = items[i].name!!.toInt()
                                }
                            }
                        }

                        //DetailsWokActivityStep1.
                    }
                }else
                    DetailsWokActivityStep1.layout_quantity.visibility = View.GONE
            } else
                DetailsWokActivityStep1.layout_quantity.visibility = View.GONE*/
        }
        else {
           /* DetailsWokActivityStep1.selectedProtein.removeAll {
                it.id.contains(protein.id!!,false)
            }*/
            DetailsWokActivityStep1.adapterRecyclerViewQuantite.removeData(protein.id!!)

            //DetailsWokActivityStep1.layout_quantity.visibility = View.GONE
        }
        //DetailsWokActivityStep1.layout_quantity

    }

    fun unselectProteine(id : String){
        adapter.unselectItem(id)
    }

    fun unselectAll(){
        adapter.unselectAll()
    }




}