package fr.mi.wewok.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel (
        @SerializedName("id")
        val id: Int,
        @SerializedName("username")
        val username:String,
        @SerializedName("nicename")
        val nicename:String,
        @SerializedName("email")
        val email:String,
        @SerializedName("url")
        val url:String,
        @SerializedName("registered")
        val registered:String,
        @SerializedName("displayname")
        val displayname:String,
        @SerializedName("firstname")
        val firstname:String,
        @SerializedName("lastname")
        val lastname:String,
        @SerializedName("nickname")
        val nickname:String,
        @SerializedName("description")
        val description:String,
        @SerializedName("capabilities")
        val capabilities:String,
        @SerializedName("avatar")
        val avatar:String) : Parcelable

