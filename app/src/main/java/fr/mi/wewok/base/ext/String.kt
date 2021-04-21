package fr.mi.wewok.base.ext

fun String?.safeToString():String = this ?: ""