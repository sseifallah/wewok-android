package fr.mi.wewok.ui.auth.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import cn.pedant.SweetAlert.SweetAlertDialog
import fr.mi.wewok.R
import fr.mi.wewok.data.model.ConnectModelR
import fr.mi.wewok.ui.auth.AuthActivity
import com.mobilemovement.kotlintvmaze.base.Resource
import com.mobilemovement.kotlintvmaze.base.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val viewModel: ConnectViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txt_s_inscrire.setOnClickListener { openSignup() }
        txt_psw.setOnClickListener { openPassword() }
        btn_login.setOnClickListener {
            if (edit_email.text!!.trim().isNotEmpty() && edit_password.text!!.trim().isNotEmpty())
                connect(edit_email.text.toString(),edit_password.text.toString())
            else
                Toast.makeText(requireContext(), "Entrez votre email & mot de pass", Toast.LENGTH_SHORT).show()

        }
    }

    fun openSignup(){
        (activity as AuthActivity).signupFragment()
    }

    fun openPassword(){
        (activity as AuthActivity).passwordFragment()
    }

    fun connect(email : String, password: String){
        if (isConnected()) {
            viewModel.connect(email, password)
            pg_login.visibility = View.VISIBLE
            viewModel.connectLiveData.observe(viewLifecycleOwner, loginObserver)
        }
        else
            errorMessage()
    }

    var loginObserver: Observer<Resource<ConnectModelR>> =
        object : Observer<Resource<ConnectModelR>> {
            override fun onChanged(it: Resource<ConnectModelR>?) {
                pg_login.visibility = View.INVISIBLE
                when (it!!.status){
                    Status.SUCCESS ->{
                        Log.i("CONNECT_REQ","SUCCESS")
                        Toast.makeText(requireContext(), "Succès", Toast.LENGTH_SHORT).show()

                        (activity as AuthActivity).toHome()
                    }
                    Status.NO_INTERNET ->{
                        Log.i("CONNECT_REQ","NO_INTERNET")
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else->{
                        Log.i("CONNECT_REQ","ELSE")
                        Toast.makeText(requireContext(), "Une erreur, veuillez vérifier vos données", Toast.LENGTH_SHORT).show()
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