package fr.mi.wewok.ui.auth.fragments

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
import fr.mi.wewok.R
import fr.mi.wewok.data.model.ResetPasswordModel
import fr.mi.wewok.ui.auth.AuthActivity
import com.mobilemovement.kotlintvmaze.base.Resource
import com.mobilemovement.kotlintvmaze.base.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_code.btn_validate
import kotlinx.android.synthetic.main.fragment_set_password.*

@AndroidEntryPoint
class SetPasswordFragment(var email: String , var code: String) : Fragment() {
    private val viewModel: ConnectViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_password, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_validate.setOnClickListener {
            if (edit_setPass.text!!.trim().isNotEmpty())
                updatePassword(email,code,edit_setPass.text.toString())
            else
                Toast.makeText(requireContext(), "Entrez votre mot de passe", Toast.LENGTH_SHORT).show()

        }
    }


    fun updatePassword(email : String, code: String, password:String){
        if (isConnected()) {
            viewModel.updatePassword(email, code,password)
            pg_setPass.visibility = View.VISIBLE
            viewModel.updateLiveData.observe(viewLifecycleOwner, updateObserver)
        }
        else
            errorMessage()
    }

    var updateObserver: Observer<Resource<ResetPasswordModel>> =
        object : Observer<Resource<ResetPasswordModel>> {
            override fun onChanged(it: Resource<ResetPasswordModel>?) {
                Log.i("VALIDATE_REQ","RES $it")
                pg_setPass.visibility = View.INVISIBLE
                when (it!!.status){
                    Status.SUCCESS ->{
                        Log.i("UPDATE_REQ","SUCCESS")
                        Toast.makeText(requireContext(), "${it.data?.message}", Toast.LENGTH_SHORT).show()
                        edit_setPass.text?.clear()
                        (activity as AuthActivity).toLogin()
                    }
                    Status.NO_INTERNET ->{
                        Log.i("UPDATE_REQ","NO_INTERNET")
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else->{
                        Log.i("UPDATE_REQ","ELSE")
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
