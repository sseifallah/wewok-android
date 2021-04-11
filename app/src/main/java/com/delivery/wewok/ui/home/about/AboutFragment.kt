package com.delivery.wewok.ui.home.about

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.delivery.wewok.R
import kotlinx.android.synthetic.main.fragment_about.*


class AboutFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txt_desc_details.setMovementMethod(ScrollingMovementMethod())


        /* val cdd = CustomDialogClass(activity!!,true)
        cdd.getWindow()?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));
        cdd.setCanceledOnTouchOutside(false)
        cdd.show()*/
    }

}