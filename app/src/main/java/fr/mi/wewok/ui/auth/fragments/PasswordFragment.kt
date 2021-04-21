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
import fr.mi.wewok.data.model.ResetPasswordModel
import fr.mi.wewok.ui.auth.AuthActivity
import com.mobilemovement.kotlintvmaze.base.Resource
import com.mobilemovement.kotlintvmaze.base.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_password.*

@AndroidEntryPoint
class PasswordFragment : Fragment() {
    private val viewModel: ConnectViewModel by viewModels()
    var email : String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_password, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         btn_send.setOnClickListener {

             if (edit_email_reset.text!!.trim().isNotEmpty())
                 resetPass(edit_email_reset.text.toString())
             else
                 Toast.makeText(requireContext(), "Entrez votre email", Toast.LENGTH_SHORT).show()

         }
    }
    fun resetPass(email : String){
        if (isConnected()) {
            viewModel.resetPassword(email)
            pg_pass.visibility = View.VISIBLE
            viewModel.resetLiveData.observe(viewLifecycleOwner, resetObserver)
        }
        else
            errorMessage()
    }

    var resetObserver: Observer<Resource<ResetPasswordModel>> =
            object : Observer<Resource<ResetPasswordModel>> {
                override fun onChanged(it: Resource<ResetPasswordModel>?) {
                    pg_pass.visibility = View.INVISIBLE
                    when (it!!.status){
                        Status.SUCCESS ->{
                            Log.i("RESET_REQ","SUCCESS $it")
                            Toast.makeText(requireContext(), "${it.data?.message}", Toast.LENGTH_SHORT).show()
                            email = edit_email_reset.text.toString()
                            edit_email_reset.text?.clear()
                            (activity as AuthActivity).toCode(email)
                        }
                        Status.NO_INTERNET ->{
                            Log.i("RESET_REQ","NO_INTERNET")
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        else->{
                            Log.i("RESET_REQ","ELSE")
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