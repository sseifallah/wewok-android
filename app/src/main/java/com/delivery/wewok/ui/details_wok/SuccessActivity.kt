 package com.delivery.wewok.ui.details_wok

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.delivery.wewok.R
import com.delivery.wewok.ui.details_wok.commande.CommandeActivity
import com.delivery.wewok.ui.details_wok.commande.CommandesViewModel
import com.delivery.wewok.ui.details_wok.payement.PaymentEmporterActivity
import com.delivery.wewok.ui.details_wok.step1.DetailsWokActivityStep1
import com.delivery.wewok.ui.details_wok.step2.DetailsWokActivityStep2
import com.delivery.wewok.ui.details_wok.step3.DetailsWokActivityStep3
import com.delivery.wewok.ui.home.HomeActivity
import com.delivery.wewok.utils.ORDER_ID
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_success.*

 @AndroidEntryPoint
class SuccessActivity : AppCompatActivity() {
     private val viewModel: CommandesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_success)
        var numCmd = Paper.book().read<Int>(ORDER_ID)
        viewModel.updateStatus(numCmd,"approved")
        CommandeActivity.commandes.clear()
        CommandeActivity.allCommandes.clear()
        DetailsWokActivityStep1.commandes.clear()
        DetailsWokActivityStep2.commandes.clear()
        DetailsWokActivityStep3.commandes.clear()
        txt_cmd.text ="Votre numéro de commande \nest : $numCmd"
        bottom_retour.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}

