package com.delivery.wewok.ui.home.profil

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
import com.delivery.wewok.data.model.*
import com.delivery.wewok.ui.auth.fragments.ConnectViewModel
import com.delivery.wewok.ui.home.HomeActivity
import com.delivery.wewok.ui.home.home.HomeViewModel
import com.delivery.wewok.utils.*
import com.mobilemovement.kotlintvmaze.base.Resource
import com.mobilemovement.kotlintvmaze.base.Status
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_profil.*

@AndroidEntryPoint
class ProfilFragment: Fragment() {
     var username : String? = null
     var firstName : String? = null
     var lastName : String? = null
     var email : String? = null
     var phone : String? = null
     var ville : String? = null
     var adresse : String? = null
     var code : String? = null

    var editUsername : String = ""
    var editFirstName : String = ""
    var editLastName : String = ""
    var editEmail :  String = ""
    var editPhone :  String = ""
    var editVille :  String = ""
    var editAdresse :  String = ""
    var editCode : String = ""

    private val viewModel: ConnectViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    var userId =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userId = Paper.book().read(IID)
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*if (payment){
            btn_back.visibility = View.VISIBLE
            btn_back.setOnClickListener {
                (activity as PaymentActivity).setFragmentVisibility()
            }
            btn_logout.visibility = View.INVISIBLE
        }*/
        getUserInfos()
        btn_logout.setOnClickListener {
            (activity as HomeActivity).logout()
        }
        setupNavigation()
    }

    fun getUserInfos(){

        if (Paper.book().contains(USERNAME)) {
            username = Paper.book().read<String>(USERNAME)
        }
        if (Paper.book().contains(FIRST_NAME)) {
            firstName = Paper.book().read<String>(FIRST_NAME)
        }
        if (Paper.book().contains(LAST_NAME) ){
            lastName = Paper.book().read<String>(LAST_NAME)
        }
        if (Paper.book().contains(USER_EMAIL)){
            email = Paper.book().read<String>(USER_EMAIL)
        }
        if (Paper.book().contains(USER_NUM)){
            phone = Paper.book().read<String>(USER_NUM)
        }
        if (Paper.book().contains(USER_ADR)){
            adresse = Paper.book().read<String>(USER_ADR)
        }
        if (Paper.book().contains(USER_VILLE)){
            ville = Paper.book().read<String>(USER_VILLE)
        }
        if (Paper.book().contains(CODE_ZONE)){
            code = Paper.book().read<String>(CODE_ZONE)
        }


        if (username!= null) {
            txt_name.setText(username)
        }
        if (firstName!= null) {
            edit_prenom.setText(firstName)
        }
        if (lastName!= null) {
            edit_nom.setText(lastName)
        }
        if (email!= null) {
            edit_email.setText(email)
        }
        if (phone!= null) {
            edit_phone.setText(phone)
        }
        if (adresse!= null) {
            edit_adresse.setText(adresse)
        }
        if (ville!= null) {
            edit_ville.setText(ville)
        }
        if (code!= null) {
            edit_code.setText(code)
        }

    }

    fun setUserInfos(user : UserApiModel){

        firstName = user.data.first_name
        lastName = user.data.last_name
        email = user.data.client_email
        phone = user.data.client_phone
        adresse = user.data.adresse
        ville = user.data.ville
        code = user.data.postal_code


        if (username!= null) {
            txt_name.setText(username)
        }
        if (firstName!= null) {
            edit_prenom.setText(firstName)
        }
        if (lastName!= null) {
            edit_nom.setText(lastName)
        }
        if (email!= null) {
            edit_email.setText(email)
        }
        if (phone!= null) {
            edit_phone.setText(phone)
        }
        if (adresse!= null) {
            edit_adresse.setText(adresse)
        }
        if (ville!= null) {
            edit_ville.setText(ville)
        }
        if (code!= null) {
            edit_code.setText(code)
        }

       /* if (payment){
           // (activity as PaymentActivity).setFragmentVisibility()

        }*/

    }

    fun setupNavigation() {
        btn_save.setOnClickListener {
            if (!edit_prenom.text.isEmpty())
                editFirstName = edit_prenom.text.toString()
            if (!edit_nom.text?.isEmpty()!!)
                editLastName = edit_nom.text.toString()
            if (!edit_email.text.isEmpty())
                editEmail = edit_email.text.toString()
            if (!edit_phone.text.isEmpty())
                editPhone = edit_phone.text.toString()
            if (!edit_adresse.text.isEmpty())
                editAdresse = edit_adresse.text.toString()
            if (!edit_ville.text.isEmpty())
                editVille = edit_ville.text.toString()
            if (!edit_code.text.isEmpty())
                editCode = edit_code.text.toString()

            if (!editFirstName.equals("") &&
                    !editLastName.equals("") &&
                    !editEmail.equals("") &&
                    !editCode.equals("") &&
                    !editPhone.equals("") &&
                    !editAdresse.equals("") &&
                    !editVille.equals("")) {

                if (!edit_prenom.text.equals(firstName) ||
                        !edit_nom.text?.equals(lastName)!! ||
                        !edit_email.text.equals(email) ||
                        !edit_phone.text.equals(phone) ||
                        !edit_adresse.text.equals(adresse) ||
                        !edit_ville.text.equals(ville) ||
                        !edit_code.text.equals(code)) {


                    viewModel.updateUser(
                            userId,
                            edit_prenom.text.toString(),
                            edit_nom.text.toString(),
                            edit_email.text.toString(),
                            edit_phone.text.toString(),
                            edit_adresse.text.toString(),
                            edit_ville.text.toString(),
                            edit_code.text.toString())
                    pg_profil.visibility = View.VISIBLE
                    editUsername = ""
                    editFirstName = ""
                    editLastName = ""
                    editEmail = ""
                    editPhone = ""
                    editVille = ""
                    editAdresse = ""

                    viewModel.updateUserLiveData.observe(requireActivity(), updateObserver)
                }
            } else if (edit_prenom.text.isEmpty())
                edit_prenom.error = "Entrez votre prénom"
            else if (edit_nom.text?.equals("")!!)
                edit_nom.error = "Entrez votre nom"
            else if (edit_email.text.equals(""))
                edit_email.error = "Entrez votre email"
            else if (edit_phone.text.equals(""))
                edit_phone.error = "Entrez votre numéro"
            else if (edit_adresse.text.equals(""))
                edit_adresse.error = "Entrez votre adresse"
            else if (edit_ville.text.equals(""))
                edit_ville.error = "Entrez votre ville"
            else if (edit_code.text.isEmpty())
                edit_code.error = "Entrez votre code postal"


        }

    }

    var updateObserver: Observer<Resource<UpdateUserResponse>> =
            object : Observer<Resource<UpdateUserResponse>> {
                override fun onChanged(it: Resource<UpdateUserResponse>?) {
                    pg_profil.visibility = View.INVISIBLE
                    Log.i("UPDATE_REQ","RES $it")
                    when (it!!.status){
                        Status.SUCCESS ->{
                            Log.i("UPDATE_REQ","RES ${it.data?.result}")
                            Log.i("UPDATE_REQ","RES ${it.data?.result_code}")
                            if (it.data?.result == true){
                                Toast.makeText(requireContext(), "Mise à jour réussite", Toast.LENGTH_SHORT).show()
                                homeViewModel.checkZone(editCode)
                                homeViewModel.zoneLiveData.observe(this@ProfilFragment,zoneObserver)
                                editCode  = ""
                                getUserInfo()
                            }

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


    var zoneObserver: Observer<Resource<ZoneModel>> =
            object : Observer<Resource<ZoneModel>> {
                override fun onChanged(it: Resource<ZoneModel>?) {
                    pg_profil.visibility = View.INVISIBLE
                    Log.i("ZONE_REQ Result","$it")
                    when (it!!.status){
                        Status.SUCCESS ->{
                            Log.i("ZONE_REQ","SUCCESS")
                            //Toast.makeText(requireContext(), "SUCCESS", Toast.LENGTH_SHORT).show()
                        }
                        Status.NO_INTERNET ->{
                            Log.i("ZONE_REQ","NO_INTERNET")
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        else->{
                            Log.i("ZONE_REQ","ELSE")
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }


    fun  getUserInfo(){
        viewModel.userInfos(userId)
        pg_profil.visibility = View.VISIBLE
        viewModel.userInfosLiveData.observe(requireActivity(), infosObserver)

    }
    var infosObserver: Observer<Resource<UserApiModel>> =
            object : Observer<Resource<UserApiModel>> {
                override fun onChanged(it: Resource<UserApiModel>?) {
                    pg_profil.visibility = View.INVISIBLE
                    Log.i("VALIDATE_REQ","RES $it")
                    when (it!!.status){
                        Status.SUCCESS ->{
                            if (it.data?.status == true){
                                setUserInfos(it.data)

                            }

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