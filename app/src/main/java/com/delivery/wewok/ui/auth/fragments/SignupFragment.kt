package com.delivery.wewok.ui.auth.fragments

import android.content.Context
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
import cn.pedant.SweetAlert.SweetAlertDialog
import com.delivery.wewok.R
import com.delivery.wewok.data.model.ConnectModelR
import com.delivery.wewok.data.model.RegisterModel
import com.delivery.wewok.data.model.RegisterModelR
import com.delivery.wewok.ui.auth.AuthActivity
import com.mobilemovement.kotlintvmaze.base.Resource
import com.mobilemovement.kotlintvmaze.base.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_signup.*


@AndroidEntryPoint
class SignupFragment : Fragment() {
    var email : String=""
    var password : String =""
    private val viewModel: ConnectViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txt_s_identifier.setOnClickListener { openLogin() }
        btn_signup.setOnClickListener {
            if (edit_nom.text!!.trim().isNotEmpty() && edit_prenom.text!!.trim().isNotEmpty() && edit_email_register.text!!.trim().isNotEmpty() && edit_phone.text!!.trim().isNotEmpty() &&
                    edit_password_register.text!!.trim().isNotEmpty() &&  edit_adresse.text!!.trim().isNotEmpty() ) {
                email = edit_email_register.text.toString()
                password = edit_password_register.text.toString()
                register(edit_nom.text.toString(), edit_prenom.text.toString(), edit_email_register.text.toString(), edit_phone.text.toString(), edit_password_register.text.toString(), edit_adresse.text.toString())

            }
                else
                Toast.makeText(requireContext(), "Entrez tout les champs", Toast.LENGTH_SHORT).show()

        }
    }

    fun register(nom : String, prenom : String, email : String, tel : String, password : String, adresse : String){
        if (isConnected()) {
            viewModel.signup(nom, prenom, email, tel, password,adresse)
            pg_signup.visibility = View.VISIBLE
            viewModel.registerLiveData.observe(viewLifecycleOwner, registerObserver)
        }
        else
            errorMessage()
    }

    var registerObserver: Observer<Resource<RegisterModelR>> =
        object : Observer<Resource<RegisterModelR>> {
            override fun onChanged(it: Resource<RegisterModelR>?) {

                when (it!!.status){
                    Status.SUCCESS ->{
                        Log.i("REGISTER_REQ","SUCCESS")
                        //Toast.makeText(requireContext(), "Succès", Toast.LENGTH_SHORT).show()
                        connect(email,password)
                    }
                    Status.NO_INTERNET ->{
                        pg_signup.visibility = View.INVISIBLE
                        Log.i("REGISTER_REQ","NO_INTERNET")
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else->{
                        pg_signup.visibility = View.INVISIBLE
                        Log.i("REGISTER_REQ","ELSE")
                        Toast.makeText(requireContext(), "Une erreur, veuillez vérifier vos données", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    fun connect(email : String, password: String){
        if (isConnected()) {
            viewModel.connect(email, password)
            pg_signup.visibility = View.VISIBLE
            viewModel.connectLiveData.observe(viewLifecycleOwner, loginObserver)
        }
        else
            errorMessage()
    }

    var loginObserver: Observer<Resource<ConnectModelR>> =
            object : Observer<Resource<ConnectModelR>> {
                override fun onChanged(it: Resource<ConnectModelR>?) {
                    pg_signup.visibility = View.INVISIBLE
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


    fun login(){
        (activity as AuthActivity).loginFragment()
    }

    fun openLogin(){
        (activity as AuthActivity).loginFragment()
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