 package com.delivery.wewok.ui.details_wok

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.delivery.wewok.R
import com.delivery.wewok.ui.details_wok.payement.PaymentEmporterActivity
import com.delivery.wewok.ui.home.HomeActivity
import com.delivery.wewok.utils.ORDER_ID
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_success.*


class SuccessActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_success)
        var numCmd = Paper.book().read<Int>(ORDER_ID)
        txt_cmd.text ="Votre numéro de commande \nest : $numCmd"
        bottom_retour.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}

