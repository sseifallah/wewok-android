package com.delivery.wewok.ui.details_wok.step1.autre

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.delivery.wewok.R
import com.delivery.wewok.base.ext.init
import com.delivery.wewok.data.model.CommandeModel
import com.delivery.wewok.ui.details_wok.step1.DetailsWokActivityStep1
import com.delivery.wewok.ui.details_wok.step1.fromage.FromageAdapter
import com.delivery.wewok.ui.home.HomeActivity
import com.mobilemovement.kotlintvmaze.base.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_autre.*

@AndroidEntryPoint
class AutreFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_autre, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        adapter.setData(HomeActivity.menu.autres)
        //observeViewModel()
    }

    private fun initUi()
    {
        adapter = AutreAdapter(requireContext(),this::onClickItem)
        recyclerview.init(adapter, LinearLayoutManager.HORIZONTAL)
    }

    private fun onClickItem(position:Int)
    {
        var autre  = adapter.selectOrUnSelect(position)
        if (autre.selected){
            DetailsWokActivityStep1.layout_quantity.visibility = View.VISIBLE
            //  DetailsWokActivityStep1.selectedAutre.add(CommandeModel(autre.id!!,autre.title!!,autre.price!!,autre.image,1,true,4))
            DetailsWokActivityStep1.adapterRecyclerViewQuantite.addData(CommandeModel(autre.id!!,autre.title!!,autre.price!!,autre.image,1,false,4))
        }

        else
            DetailsWokActivityStep1.adapterRecyclerViewQuantite.removeData(autre.id!!)
    }

    fun unselectAutre(id : String){
        adapter.unselectItem(id)
    }

    fun unselectAll(){
        adapter.unselectAll()
    }


    companion object {
        @JvmStatic
        fun newInstance() = AutreFragment()

        @JvmStatic
        fun getName(context: Context) = context.getString(R.string.Autres)

        @JvmStatic
        lateinit var adapter: AutreAdapter
    }
}