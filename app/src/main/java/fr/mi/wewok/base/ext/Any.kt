package fr.mi.wewok.base.ext

import android.util.Log

fun Any.log(message:String)
{
   Log.wtf(this.javaClass.name,message)
}