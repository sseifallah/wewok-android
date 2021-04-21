package fr.mi.wewok.ui.details_wok.step1.fruits

import android.content.Context
import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_fruits.*

@AndroidEntryPoint
class FruitsFragment : Fragment() {



    private lateinit var adapter: FruitsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fruits, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // sendOnStartEvent()
        initUi()
        adapter.setData(HomeActivity.menu.fruits)
       // observeViewModel()
    }


    private fun initUi()
    {
        adapter = FruitsAdapter(requireContext(),this::onClickItem)
        recyclerviewFruits.init(adapter, LinearLayoutManager.HORIZONTAL)
    }

    private fun onClickItem(position:Int)
    {
        var fruit = adapter.selectOrUnSelect(position)
        if (fruit.selected){
            DetailsWokActivityStep1.layout_quantity.visibility = View.VISIBLE
            // DetailsWokActivityStep1.selectedFruit.add(CommandeModel(fruit.id!!,fruit.title!!,fruit.price!!,fruit.image,1,true,3))
            DetailsWokActivityStep1.adapterRecyclerViewQuantite.addData(CommandeModel(fruit.id!!,fruit.title!!,fruit.price!!,fruit.image,1,false,3))
        }

        else
            DetailsWokActivityStep1.adapterRecyclerViewQuantite.removeData(fruit.id!!)

    }
    fun unselectFruit(id : String){
        adapter.unselectItem(id)
    }

    fun unselectAll(){
       adapter.unselectAll()
    }


    companion object {
        @JvmStatic
        fun newInstance() = FruitsFragment()

        @JvmStatic
        fun getName(context: Context) = context.getString(R.string.Fruits)

        @JvmStatic
        lateinit var adapter: FruitsAdapter
    }
}