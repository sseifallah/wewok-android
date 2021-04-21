 package fr.mi.wewok.ui.details_wok

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import fr.mi.wewok.R
import fr.mi.wewok.ui.details_wok.commande.CommandeActivity
import fr.mi.wewok.ui.details_wok.commande.CommandesViewModel
import fr.mi.wewok.ui.details_wok.step1.DetailsWokActivityStep1
import fr.mi.wewok.ui.details_wok.step2.DetailsWokActivityStep2
import fr.mi.wewok.ui.details_wok.step3.DetailsWokActivityStep3
import fr.mi.wewok.ui.home.HomeActivity
import fr.mi.wewok.utils.ORDER_ID
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

