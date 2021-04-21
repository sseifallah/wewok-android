package fr.mi.wewok.ui.auth

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import fr.mi.wewok.R
import fr.mi.wewok.ui.auth.fragments.*
import fr.mi.wewok.ui.home.ModeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_auth.*

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    lateinit var currentFragment : Fragment
    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        currentFragment = LoginFragment()
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, currentFragment,"loginFragment")
            .commit()
    }

    fun signupFragment(){
        slideDownCard()
        currentFragment = SignupFragment()
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            .replace(R.id.fragment_container, currentFragment)
            .addToBackStack(null)
            .commit()
    }


    fun toLogin(){
        slideUpCard()
        slideUpLogo()
        currentFragment = LoginFragment()
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                .replace(R.id.fragment_container, currentFragment,"loginFragment")
                .addToBackStack(null)
                .commit()
    }

    fun loginFragment(){
        if (isPasswordFragment())
            slideUpLogo()
        if (!isLoginFragment())
            slideUpCard()
        fragmentManager.popBackStack()
    }
    fun isPasswordFragment() : Boolean{
        val fragment = fragmentManager.findFragmentByTag("passwordFragment")
        return (fragment!= null && fragment.isVisible)
    }

    fun isCodeFragment() : Boolean{
        val fragment = fragmentManager.findFragmentByTag("codeFragment")
        return (fragment!= null && fragment.isVisible)
    }

    fun isLoginFragment() : Boolean{
        val fragment = fragmentManager.findFragmentByTag("loginFragment")
        return (fragment!= null && fragment.isVisible)
    }



    fun passwordFragment(){
        slideDownCard()
        slideDownLogo()
        currentFragment = PasswordFragment()
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            .replace(R.id.fragment_container, currentFragment, "passwordFragment")
            .addToBackStack(null)
            .commit()
    }

    private fun slideDownCard()
    {
        val anim = ValueAnimator.ofFloat(0.07f, 0.12f)
        anim.addUpdateListener { valueAnimator ->

            val valeur = valueAnimator.animatedValue as Float
            val param: ConstraintLayout.LayoutParams = topCardGuideline.layoutParams as ConstraintLayout.LayoutParams
            param.guidePercent = valeur
            topCardGuideline.layoutParams = param
        }
        anim.duration = 250
        anim.start()
        anim.doOnEnd { btn_back_login.visibility = View.VISIBLE }
    }

    private fun slideUpCard()
    {
        btn_back_login.visibility = View.INVISIBLE
        val anim = ValueAnimator.ofFloat(0.12f, 0.07f)
        anim.addUpdateListener { valueAnimator ->

            val valeur = valueAnimator.animatedValue as Float
            val param: ConstraintLayout.LayoutParams = topCardGuideline.layoutParams as ConstraintLayout.LayoutParams
            param.guidePercent = valeur
            topCardGuideline.layoutParams = param
        }
        anim.duration = 250
        anim.start()

    }

    private fun slideDownLogo()
    {
        val anim = ValueAnimator.ofFloat(0.15f, 0.23f)
        anim.addUpdateListener { valueAnimator ->

            val valeur = valueAnimator.animatedValue as Float
            val param: ConstraintLayout.LayoutParams = topLogoGuideline.layoutParams as ConstraintLayout.LayoutParams
            param.guidePercent = valeur
            topLogoGuideline.layoutParams = param

            val valeur2 = valueAnimator.animatedValue as Float
            val param2: ConstraintLayout.LayoutParams = bottomLogoGuideline.layoutParams as ConstraintLayout.LayoutParams
            param2.guidePercent = valeur2 + 0.1f
            bottomLogoGuideline.layoutParams = param2
        }
        anim.duration = 250
        anim.start()
    }

    private fun slideUpLogo()
    {
        val anim = ValueAnimator.ofFloat(0.23f, 0.15f)
        anim.addUpdateListener { valueAnimator ->

            val valeur = valueAnimator.animatedValue as Float
            val param: ConstraintLayout.LayoutParams = topLogoGuideline.layoutParams as ConstraintLayout.LayoutParams
            param.guidePercent = valeur
            topLogoGuideline.layoutParams = param

            val valeur2 = valueAnimator.animatedValue as Float
            val param2: ConstraintLayout.LayoutParams = bottomLogoGuideline.layoutParams as ConstraintLayout.LayoutParams
            param2.guidePercent = valeur2 + 0.1f
            bottomLogoGuideline.layoutParams = param2
        }
        anim.duration = 250
        anim.start()
    }


    fun pressBtnBackLogin(view: View) {
        onBackPressed()
    }

    fun  toHome(){
        startActivity(Intent(this, ModeActivity::class.java))
        finish()
    }
    override  fun onBackPressed() {
       // super.onBackPressed()
        if (!isCodeFragment()) {
            loginFragment()
        }
        else if (isLoginFragment())
            finish()
        else
            super.onBackPressed()

    }
    fun toCode(email : String ){
        currentFragment = CodeFragment(email)
        fragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            .replace(R.id.fragment_container, currentFragment, "codeFragment")
            .addToBackStack(null)
            .commit()
    }

    fun toSetPassword(email : String, code: String ){
        currentFragment = SetPasswordFragment(email,code)
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.fragment_container, currentFragment, "setPasswordFragment")
                .addToBackStack(null)
                .commit()
    }

}