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
import fr.mi.wewok.utils.IID
import fr.mi.wewok.utils.TOKEN
import fr.mi.wewok.utils.notifications.NotificationsViewModel
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_success.*

 @AndroidEntryPoint
class SuccessActivity : AppCompatActivity() {
     private val viewModel: CommandesViewModel by viewModels()
     private val viewModelNotifications: NotificationsViewModel by viewModels()
     var numCmd:Int = 0
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        numCmd = Paper.book().read<Int>(ORDER_ID)
         sendNotif()
        setContentView(R.layout.fragment_success)


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

     fun sendNotif(){
         var id =  Paper.book().read<Int>(IID)
         viewModelNotifications.sendNotifiction(id,"Commande enregistré avec succès!","Votre numéro de commande \nest : $numCmd")
     }
}

