package com.delivery.wewok.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.delivery.wewok.R
import com.delivery.wewok.data.model.MenuItemRawModel
import com.delivery.wewok.data.model.WoksRawModel
import com.delivery.wewok.ui.auth.AuthActivity
import com.delivery.wewok.ui.auth.fragments.LoginFragment
import com.delivery.wewok.ui.details_wok.commande.CommandeActivity
import com.delivery.wewok.ui.details_wok.step1.DetailsWokActivityStep1
import com.delivery.wewok.ui.home.about.AboutFragment
import com.delivery.wewok.ui.home.contact.ContactFragment
import com.delivery.wewok.ui.home.home.HomeFragment
import com.delivery.wewok.ui.home.profil.CommandesFragment
import com.delivery.wewok.ui.home.profil.ProfilFragment
import com.delivery.wewok.utils.*
import dagger.hilt.android.AndroidEntryPoint
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
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