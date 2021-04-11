package com.delivery.wewok.ui.details_wok.payement

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.delivery.wewok.R
import com.delivery.wewok.base.ext.toast
import com.delivery.wewok.ui.details_wok.SuccessActivity
import com.delivery.wewok.ui.details_wok.commande.CommandesViewModel
import com.delivery.wewok.utils.ORDER_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_web.*

@AndroidEntryPoint
class WebActivity : AppCompatActivity() {
    private val viewModel: CommandesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        var orderId = intent.getIntExtra(ORDER_ID,0)
        var url = intent.getStringExtra("URL")
        var successUrl = intent.getStringExtra("Success_URL")
        var errorUrl = intent.getStringExtra("Error_URL")
        web.loadUrl(url!!)
        Log.i("WEB_URL", " : loading")
        web.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                // Here put your code
                Log.i("WEB_URL", url)
                if (url.equals(errorUrl)){
                    Log.i("WEB_URL", "Error")
                    toast("Paiement non effectué")
                    this@WebActivity.finish()
                } else if (url.equals(successUrl)){
                    Log.i("WEB_URL", "Success")
                    viewModel.updateStatus(orderId,"approved")

                    val intent = Intent(baseContext, SuccessActivity::class.java)
                    startActivity(intent)
                }
                Log.i("WEB_URL", url)

                // return true; //Indicates WebView to NOT load the url;
                return false //Allow WebView to load url
            }


        }
    }
}