package fr.mi.wewok.ui.home.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import fr.mi.wewok.R
import fr.mi.wewok.base.ext.init
import fr.mi.wewok.data.model.MenuItemRawModel
import fr.mi.wewok.ui.details_wok.step1.DetailsWokActivityStep1
import fr.mi.wewok.ui.home.HomeActivity
import com.mobilemovement.kotlintvmaze.base.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    companion object {
        lateinit var model : MenuItemRawModel
    }

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var adapter: WokAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        val cdd = CustomDialogClass(requireActivity(),false)
        cdd.getWindow()?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));
        cdd.setCanceledOnTouchOutside(false)
        cdd.show()
         */
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pg_home.visibility = View.VISIBLE
        initUi()
        viewModel.getWoks()
        viewModel.woksLiveData.observe(viewLifecycleOwner, Observer {
            Log.i("WOK_RESP : "," ${it.data} ")
            when (it.status){
                Status.SUCCESS ->{
                    Log.i("WOK_RESP : "," ${it.data} ")
                    HomeActivity.menu = it.data!!
                    pg_home.visibility = View.INVISIBLE
                    adapter.setData(it.data.woks)
                }
                Status.NO_INTERNET ->{
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                else->{
                    //Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        btn_cart.setOnClickListener {
            (activity as HomeActivity).toCommandes()
        }
    }

    private fun initUi()
    {
        adapter = WokAdapter(requireContext(),this::onClickItem)
        recyclerview.init(adapter, LinearLayoutManager.VERTICAL)
    }

    private fun onClickItem(position:Int)
    {
        HomeActivity.model = adapter.getItem(position)
       var intent = Intent(requireContext(), DetailsWokActivityStep1::class.java)
       startActivity(intent)
    }

    private fun observeViewModel() = with(viewModel){

        woksLiveData.observe(viewLifecycleOwner, Observer {
            Log.i("WOK_RESP : "," ${it.data} ")
            when (it.status){

                Status.SUCCESS ->{
                    Log.i("WOK_RESP : "," ${it.data} ")
                    pg_home.visibility = View.INVISIBLE
                    adapter.setData(it.data!!.woks)
                }
                Status.NO_INTERNET ->{
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                else->{
                    //Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}