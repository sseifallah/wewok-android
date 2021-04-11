package com.delivery.wewok.base.ext

import android.util.Log
import android.webkit.URLUtil
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.lang.Exception

fun ImageView.setImage(image:String?,title : String)
{
    val isImageFromServer = URLUtil.isValidUrl(image)
    if(isImageFromServer)
    {
        Glide.with(context).load(image).into(this)
    }else
    {
        var drawableId : Int
        try {
            if (image.isNullOrEmpty()){
                Log.i("CMD_Adapter"," is null")

                    if (title.contains("Nouilles de blé"))
                        drawableId = context.resources.getIdentifier("img_base_1", "drawable", this.context.packageName)
                    else if (title.contains("Nouilles de riz"))
                        drawableId = context.resources.getIdentifier("img_base_2", "drawable", this.context.packageName)

                    else if (title.contains("Riz Thaï"))
                        drawableId = context.resources.getIdentifier("img_base_3", "drawable", this.context.packageName)
                    else if (title.contains("Légumes sautés"))
                        drawableId = context.resources.getIdentifier("img_base_4", "drawable", this.context.packageName)
                    else if (title.contains("Nouilles Udon"))
                        drawableId = context.resources.getIdentifier("img_base_5", "drawable", this.context.packageName)
                    else if (title.contains("Sans légumes"))
                        drawableId = context.resources.getIdentifier("img_retier_ingredient_1", "drawable", this.context.packageName)
                    else if (title.contains("Sans pousse de soja"))
                        drawableId = context.resources.getIdentifier("img_retier_ingredient_2", "drawable", this.context.packageName)
                    else if (title.contains("Sans oeuf"))
                        drawableId = context.resources.getIdentifier("img_retier_ingredient_3", "drawable", this.context.packageName)
                    else
                        drawableId = context.resources.getIdentifier("img_base_1", "drawable", this.context.packageName)
            }

            else {
                drawableId = context.resources.getIdentifier(image, "drawable", this.context.packageName)
            }
            Glide.with(this.context).load(drawableId).into(this)
        }catch (ex:Exception){ }
    }
}