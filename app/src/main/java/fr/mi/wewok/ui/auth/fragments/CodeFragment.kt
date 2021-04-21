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
import kotlinx.android.synthetic.main.fragment_code.*

@AndroidEntryPoint
class CodeFragment(var email: String) : Fragment() {

    private val viewModel: ConnectViewModel by viewModels()
    var code : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_validate.setOnClickListener {
            if (edit_code.text!!.trim().isNotEmpty())
                validateCode(email,edit_code.text.toString())
            else
                Toast.makeText(requireContext(), "Entrez votre code", Toast.LENGTH_SHORT).show()

        }
    }


    fun validateCode(email : String, code: String){
        if (isConnected()) {
            viewModel.validateCode(email, code)
            pg_valid.visibility = View.VISIBLE
            viewModel.validateLiveData.observe(viewLifecycleOwner, loginObserver)
        }
        else
            errorMessage()
    }

    var loginObserver: Observer<Resource<ResetPasswordModel>> =
            object : Observer<Resource<ResetPasswordModel>> {
                override fun onChanged(it: Resource<ResetPasswordModel>?) {
                    Log.i("VALIDATE_REQ","RES $it")
                    pg_valid.visibility = View.INVISIBLE
                    when (it!!.status){
                        Status.SUCCESS ->{
                            Log.i("VALIDATE_REQ","SUCCESS")
                            Toast.makeText(requireContext(), "${it.data?.message}", Toast.LENGTH_SHORT).show()
                            code = edit_code.text.toString()
                            edit_code.text?.clear()
                            (activity as AuthActivity).toSetPassword(email,code)
                        }
                        Status.NO_INTERNET ->{
                            Log.i("VALIDATE_REQ","NO_INTERNET")
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        else->{
                            Log.i("VALIDATE_REQ","ELSE")
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