package fr.mi.wewok.ui.spalshScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import fr.mi.wewok.R
import fr.mi.wewok.ui.auth.AuthActivity
import fr.mi.wewok.ui.home.ModeActivity
import fr.mi.wewok.utils.CONNECTED
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    var connected =false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val animation = AnimationUtils.loadAnimation(baseContext, R.anim.fade_in)

        if(Paper.book().contains(CONNECTED)){
            connected = Paper.book().read(CONNECTED,false)
        }
        //  btn_next.startAnimation(animation)
        GlobalScope.launch (Dispatchers.Main)
        {
            delay(6000)

            btn_next.startAnimation(animation)
            btn_next.visibility = View.VISIBLE
            //textview will be invisible after the specified amount
            // of time elapses, here it is 1000 milliseconds
        }
       btn_next.setOnClickListener {
           if (connected)
               startActivity(Intent(this@SplashScreen,ModeActivity::class.java))
           else
               startActivity(Intent(this@SplashScreen,AuthActivity::class.java))
       }
    }
}