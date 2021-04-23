package fr.mi.wewok.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import fr.mi.wewok.R
import fr.mi.wewok.data.model.MenuItemRawModel
import fr.mi.wewok.data.model.WoksRawModel
import fr.mi.wewok.ui.auth.AuthActivity
import fr.mi.wewok.ui.details_wok.commande.CommandeActivity
import fr.mi.wewok.ui.details_wok.step1.DetailsWokActivityStep1
import fr.mi.wewok.ui.home.about.AboutFragment
import fr.mi.wewok.ui.home.contact.ContactFragment
import fr.mi.wewok.ui.home.home.HomeFragment
import fr.mi.wewok.ui.home.profil.CommandesFragment
import fr.mi.wewok.ui.home.profil.ProfilFragment
import fr.mi.wewok.utils.*
import dagger.hilt.android.AndroidEntryPoint
import fr.mi.wewok.utils.notifications.NotificationsViewModel
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_home.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    companion object {
        lateinit var model : MenuItemRawModel
        lateinit var menu : WoksRawModel
    }

    lateinit var currentFragment : Fragment
    private val fragmentManager = supportFragmentManager
    private val viewModelNotifications: NotificationsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setToken()
        currentFragment = HomeFragment()
        fragmentManager.beginTransaction()
            .replace(R.id.home_fragment_container, currentFragment,"homeFragment")
            .commit()
        home_bottom_navigation.setSelectedItemId(R.id.action_home);
        home_bottom_navigation.setOnNavigationItemSelectedListener(
            { item -> updateMainFragment(item.getItemId()) })

        action_home.setOnClickListener {
            val fragment = fragmentManager.findFragmentByTag("homeFragment")
            home_bottom_navigation.setSelectedItemId(R.id.action_home);
            if (!(fragment!= null && fragment.isVisible)) {
                currentFragment = HomeFragment()
                fragmentManager.beginTransaction()
                        .replace(R.id.home_fragment_container, currentFragment,"homeFragment")
                        .addToBackStack(null)
                        .commit()
            }

        }
    }

    fun setToken(){
        var id =  Paper.book().read<Int>(IID)
        var token =  Paper.book().read<String>(TOKEN)
        viewModelNotifications.setToken(id,token)

    }
    private fun updateMainFragment(itemId: Int): Boolean {

        when (itemId) {
            R.id.action_wok -> {
                currentFragment = CommandesFragment()
                fragmentManager.beginTransaction()
                    .replace(R.id.home_fragment_container, currentFragment)
                    .addToBackStack(null)
                    .commit()
            }
            R.id.action_profil -> {
                currentFragment = ProfilFragment()
                fragmentManager.beginTransaction()
                    .replace(R.id.home_fragment_container, currentFragment)
                    .addToBackStack(null)
                    .commit()
            }
            R.id.action_about -> {
                currentFragment = AboutFragment()
                fragmentManager.beginTransaction()
                    .replace(R.id.home_fragment_container, currentFragment)
                    .addToBackStack(null)
                    .commit()
            }
            R.id.action_contact -> {
                currentFragment = ContactFragment()
                fragmentManager.beginTransaction()
                    .replace(R.id.home_fragment_container, currentFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
        return true
    }

    fun toSteps(){
        startActivity(Intent(this, DetailsWokActivityStep1::class.java))
    }
    fun logout(){
        /*Paper.book().delete(USER_ID)
        Paper.book().delete(USERNAME)
        Paper.book().delete(USER_EMAIL)
        Paper.book().delete(FIRST_NAME)
        Paper.book().delete(LAST_NAME)*/
        Paper.book().destroy()
        Paper.init(applicationContext)
        Paper.book().write<Boolean>(CONNECTED,false)
        var intentLogout = Intent(this, AuthActivity::class.java)
        intentLogout.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intentLogout)

    }

    fun toCommandes(){
        startActivity(Intent(this, CommandeActivity::class.java))
    }

}