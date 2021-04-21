package com.delivery.wewok.ui.details_wok.step1.fromage

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.delivery.wewok.R
import com.delivery.wewok.base.ext.init
import com.delivery.wewok.data.model.CommandeModel
import com.delivery.wewok.ui.details_wok.step1.DetailsWokActivityStep1
import com.delivery.wewok.ui.home.HomeActivity
import com.mobilemovement.kotlintvmaze.base.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_fromage.*

@AndroidEntryPoint
class FromageFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fromage, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        adapter.setData(HomeActivity.menu.formages)
       // observeViewModel()
    }


    private fun initUi()
    {
        adapter = FromageAdapter(requireContext(),this::onClickItem)
        recyclerview.init(adapter, LinearLayoutManager.HORIZONTAL)
    }

    private fun onClickItem(position:Int)
    {
        var fromage = adapter.selectOrUnSelect(position)
        if (fromage.selected){
            DetailsWokActivityStep1.layout_quantity.visibility = View.VISIBLE
            //DetailsWokActivityStep1.selectedFromage.add(CommandeModel(fromage.id!!,fromage.title!!,fromage.price!!,fromage.image,1,true))
            DetailsWokActivityStep1.adapterRecyclerViewQuantite.addData(CommandeModel(fromage.id!!,fromage.title!!,fromage.price!!,fromage.image,1,false,2))
        }

        else
            DetailsWokActivityStep1.adapterRecyclerViewQuantite.removeData(fromage.id!!)
            /*DetailsWokActivityStep1.selectedFromage.removeAll {
                it.id == fromage.id
            }*/
    }
    fun unselectFromage(id : String){
        adapter.unselectItem(id)
    }
    fun unselectAll(){
        adapter.unselectAll()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FromageFragment()

        @JvmStatic
        fun getName(context: Context) = context.getString(R.string.Fromages)

        @JvmStatic
        lateinit var adapter: FromageAdapter
    }
}