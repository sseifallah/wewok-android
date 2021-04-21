package com.delivery.wewok.ui.home

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import cn.pedant.SweetAlert.SweetAlertDialog
import com.delivery.wewok.R
import com.delivery.wewok.base.ext.safeToString
import com.delivery.wewok.base.ext.toast
import com.delivery.wewok.data.model.ConnectModelR
import com.delivery.wewok.data.model.ZoneModel
import com.delivery.wewok.ui.auth.AuthActivity
import com.delivery.wewok.ui.auth.fragments.ConnectViewModel
import com.delivery.wewok.ui.details_wok.SuccessActivity
import com.delivery.wewok.ui.home.home.HomeViewModel
import com.delivery.wewok.ui.utils.CustomDialogClass
import com.delivery.wewok.utils.*
import com.mobilemovement.kotlintvmaze.base.Resource
import com.mobilemovement.kotlintvmaze.base.Status
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_mode.*
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.android.synthetic.main.dialog_code_postal.*
import kotlinx.android.synthetic.main.fragment_profil.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ModeActivity : AppCompatActivity() {
    private val viewModel: HomeViewModel by viewModels()
    private val viewModelC: ConnectViewModel by viewModels()
    lateinit var cdd : CustomDialogClass
    var id = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mode)
        if (Paper.book().contains(IID))
            id = Paper.book().read<Int>(IID)
        Paper.book().delete(SAVED_ORDERS)
        viewModelC.userInfos(id)

        round_emporter.setOnClickListener {
            GlobalScope.launch (Dispatchers.Main)
            {
                round_emporter.borderColor = ContextCompat.getColor(baseContext, R.color.orange)
                emporter_circle_next.borderColor = ContextCompat.getColor(baseContext, R.color.orange)
                txt_emporter.setTextColor(ContextCompat.getColor(baseContext, R.color.orange))
                ic_emporter_next.setImageResource(R.drawable.ic_next_orange)
                img_emporter.setImageResource(R.drawable.emporter)
                round_livraison.borderColor = ContextCompat.getColor(baseContext, R.color.black)
                liv_circle_next.borderColor = ContextCompat.getColor(baseContext, R.color.black)
                txt_liv.setTextColor(ContextCompat.getColor(baseContext, R.color.black))
                ic_liv_next.setImageResource(R.drawable.ic_next)
                img_livraison.setImageResource(R.drawable.ic_liv)


                delay(700)

                Paper.book().write(MODE_WOK, MODE_EMPORTER)
                startActivity(Intent(baseContext, HomeActivity::class.java))
                //textview will be invisible after the specified amount
                // of time elapses, here it is 1000 milliseconds
            }

        }

        round_livraison.setOnClickListener {
            GlobalScope.launch (Dispatchers.Main)
            {
                round_livraison.borderColor = ContextCompat.getColor(baseContext, R.color.orange)
                liv_circle_next.borderColor = ContextCompat.getColor(baseContext, R.color.orange)
                txt_liv.setTextColor(ContextCompat.getColor(baseContext, R.color.orange))
                ic_liv_next.setImageResource(R.drawable.ic_next_orange)
                img_livraison.setImageResource(R.drawable.livraison)

                round_emporter.borderColor = ContextCompat.getColor(baseContext, R.color.black)
                emporter_circle_next.borderColor = ContextCompat.getColor(baseContext, R.color.black)
                txt_emporter.setTextColor(ContextCompat.getColor(baseContext, R.color.black))
                ic_emporter_next.setImageResource(R.drawable.ic_next)
                img_emporter.setImageResource(R.drawable.ic_emp)


                delay(700)


                cdd = CustomDialogClass(this@ModeActivity, "CODE")
                cdd.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                cdd.setCanceledOnTouchOutside(false)
                cdd.show()
                cdd.bn_verifier.setOnClickListener {
                    if (cdd.edit_code.text.isEmpty()){
                        toast("Entrez voter code postal")
                    }
                    else{
                        if (isConnected()) {
                            viewModel.checkZone(cdd.edit_code.text.toString())
                            Toast.makeText(this@ModeActivity, "Vérification ...", Toast.LENGTH_SHORT).show()
                            pg_mode.visibility = View.VISIBLE
                            viewModel.zoneLiveData.observe(this@ModeActivity,zoneObserver)
                        }
                        else
                            errorMessage()
                    }

                }
            }


        }
    }

    var zoneObserver: Observer<Resource<ZoneModel>> =
            object : Observer<Resource<ZoneModel>> {
                override fun onChanged(it: Resource<ZoneModel>?) {
                    pg_mode.visibility = View.INVISIBLE
                    Log.i("ZONE_REQ Result","$it")
                    when (it!!.status){
                        Status.SUCCESS ->{
                            Log.i("ZONE_REQ","SUCCESS")

                            Toast.makeText(this@ModeActivity, "Succès", Toast.LENGTH_SHORT).show()
                            Paper.book().write(MODE_WOK, MODE_LIVRAISON)
                            startActivity(Intent(this@ModeActivity, HomeActivity::class.java))
                            cdd.dismiss()
                        }
                        Status.NO_INTERNET ->{
                            Log.i("ZONE_REQ","NO_INTERNET")
                            Toast.makeText(this@ModeActivity, it.message, Toast.LENGTH_SHORT).show()
                        }
                        else->{
                            Log.i("ZONE_REQ","ELSE")
                            Toast.makeText(this@ModeActivity, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }


    fun isConnected():Boolean{
        val connectivityManager : ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo : NetworkInfo? = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false

    }

    fun errorMessage() {
        SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...")
                .setContentText("Aucune connexion Internet trouvée !").show()
    }

}