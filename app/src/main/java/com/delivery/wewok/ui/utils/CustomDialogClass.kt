package com.delivery.wewok.ui.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat.startActivity
import com.delivery.wewok.R
import com.delivery.wewok.ui.home.HomeActivity
import kotlinx.android.synthetic.main.dialog_code_postal.*
import kotlinx.android.synthetic.main.dialog_code_postal.btn_exit
import kotlinx.android.synthetic.main.dialog_verification.*


class CustomDialogClass (var activity: Context, var verification: Boolean) : Dialog(activity) {
    var d: Dialog? = null
    lateinit var bn_oui: Button
    lateinit var bn_retour: TextView
    lateinit var bn_verifier: Button
    lateinit var edit_code : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        if (verification == true) {
            setContentView(R.layout.dialog_verification)
            initVerification()
        }
        else{
            setContentView(R.layout.dialog_code_postal)
            initZoneLivraison()
        }
        btn_exit?.setOnClickListener {
            dismiss()
        }
    }

    fun initVerification(){
        bn_oui = findViewById(R.id.btn_oui)
        bn_retour = findViewById(R.id.btn_retour)

    }

    fun initZoneLivraison(){
        bn_verifier = findViewById(R.id.btn_verifier)
        edit_code = findViewById(R.id.edit_code)
    }

}