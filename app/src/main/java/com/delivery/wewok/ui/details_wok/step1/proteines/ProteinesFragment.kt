package com.delivery.wewok.ui.details_wok.step1.proteines

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.delivery.wewok.R
import com.delivery.wewok.base.ext.init
import com.delivery.wewok.base.ext.setImage
import com.delivery.wewok.data.model.CommandeModel
import com.delivery.wewok.ui.details_wok.step1.DetailsWokActivityStep1
import com.delivery.wewok.ui.details_wok.step1.fromage.FromageAdapter
import com.delivery.wewok.ui.home.HomeActivity
import com.mobilemovement.kotlintvmaze.base.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_proteine.*

@AndroidEntryPoint
class ProteinesFragment : Fragment() {



    private lateinit var adapter:ProteinesAdapter

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
        Log.i("PROTEINS_SELECTED" , protein.title.toString() + " " +protein.selected.toString())
        if (protein.selected == true) {
            DetailsWokActivityStep1.selectedProtein = CommandeModel(protein.id!!,protein.title!!,protein.price!!,protein.image,1,true)
            /*if (!protein.items.isNullOrEmpty()) {
                if (DetailsWokActivityStep1.layout_quantity.visibility == View.GONE) {
                    DetailsWokActivityStep1.layout_quantity.visibility = View.VISIBLE
                    DetailsWokActivityStep1.img_quantity.setImage(protein.image)
                    DetailsWokActivityStep1.txt_granitur.text = protein.title
                    //DetailsWokActivityStep1.
                }
            } else*/
                DetailsWokActivityStep1.layout_quantity.visibility = View.GONE
        }
        else {
            DetailsWokActivityStep1.selectedProtein = null
            DetailsWokActivityStep1.layout_quantity.visibility = View.GONE
        }
        //DetailsWokActivityStep1.layout_quantity

    }

    fun unselectAll(){
        adapter.unselectAll()
    }



    companion object {
        @JvmStatic
        fun newInstance() = ProteinesFragment()

        @JvmStatic
        fun getName(context: Context) = context.getString(R.string.Protéines)
    }
}