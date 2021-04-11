package com.delivery.wewok.ui.home.profil

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.delivery.wewok.R
import com.delivery.wewok.base.ext.init
import com.delivery.wewok.data.model.*
import com.delivery.wewok.ui.auth.AuthActivity
import com.delivery.wewok.ui.auth.fragments.ConnectViewModel
import com.delivery.wewok.ui.details_wok.commande.CommandeAdapter
import com.delivery.wewok.ui.details_wok.commande.CommandesViewModel
import com.delivery.wewok.ui.home.HomeActivity
import com.delivery.wewok.ui.home.commandes.CommandesAdapter
import com.delivery.wewok.ui.utils.CustomDialogClass
import com.delivery.wewok.utils.IID

import com.mobilemovement.kotlintvmaze.base.Resource
import com.mobilemovement.kotlintvmaze.base.Status
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_commande.*
import kotlinx.android.synthetic.main.fragment_commandes.*
import kotlinx.android.synthetic.main.fragment_profil.*
import kotlinx.android.synthetic.main.fragment_set_password.*

@AndroidEntryPoint
class CommandesFragment : Fragment() {
    private val viewModel: ConnectViewModel by viewModels()
    var userId = 0
    lateinit var adapter : CommandesAdapter
    var commandes = ArrayList<Commandes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userId = Paper.book().read(IID)
        return inflater.inflate(R.layout.fragment_commandes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        getCommandes(userId)
    }

    private fun initUi() {
        adapter = CommandesAdapter(requireContext())
        rcy_commandes.init(adapter, LinearLayoutManager.VERTICAL)
        rcy_commandes.addItemDecoration(
                DividerItemDecoration(
                        requireContext(),
                        LinearLayoutManager.VERTICAL
                )
        )
    }

    fun getCommandes(userId : Int){
        if (isConnected()) {
            viewModel.userInfos(userId)
            pg_cmds.visibility = View.VISIBLE
            viewModel.userInfosLiveData.observe(viewLifecycleOwner, commandesObserver)
        }
        else
            errorMessage()
    }

    var commandesObserver: Observer<Resource<UserApiModel>> =
            object : Observer<Resource<UserApiModel>> {
                override fun onChanged(it: Resource<UserApiModel>?) {
                    pg_cmds.visibility = View.INVISIBLE
                    Log.i("VALIDATE_REQ","RES $it")
                    when (it!!.status){
                        Status.SUCCESS ->{
                            Log.i("CMD_REQ"," true")
                            it.data?.data?.client_orders?.let { it1 -> commandes.addAll(it1) }
                            txt_nbr.text = "Nombre des commandes: "+it.data?.data?.client_orders?.size
                          // var elm1 = ar.get("23483").asJsonObject
                           // var comd1 = Commandes(elm1.get("order_id").asString,elm1.get("order_date").asString,elm1.get("order_price").asString,elm1.get("order_status").asString)
                           //var elm2 = ar.get("23465").asJsonObject
                           // var comd2= Commandes(elm2.get("order_id").asString,elm2.get("order_date").asString,elm2.get("order_price").asString,elm2.get("order_status").asString)
                           // elm.
                           // ${it.data?.data?.client_orders!!.asJsonArray}
                          //  commandes.add(comd1)
                           // commandes.add(comd2)
                            adapter.setData(commandes)
                            adapter.notifyDataSetChanged()

                        }
                        Status.NO_INTERNET ->{
                            Log.i("CMD_REQ","NO_INTERNET")
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        else->{
                            Log.i("CMD_REQ","ELSE")
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
    fun isConnected():Boolean{
        val connectivityManager : ConnectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo : NetworkInfo? = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false

    }

    fun errorMessage() {
        SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...")
                .setContentText("Aucune connexion Internet trouvée !").show()
    }
}