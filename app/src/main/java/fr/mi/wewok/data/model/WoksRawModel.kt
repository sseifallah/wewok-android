package fr.mi.wewok.data.model

import com.google.gson.annotations.SerializedName

data class WoksRawModel(
    @SerializedName("Mon Wok")
    val woks:List<MenuItemRawModel>,

    @SerializedName("Ma Sauce")
    val sauces:List<MenuItemRawModel>,

    @SerializedName("Mes garnitures ")
    val mesGarnitures:List<MenuItemRawModel>,

    @SerializedName("Fromages ")
    val formages:List<MenuItemRawModel>,

    @SerializedName("Fruits et Legumes")
    val fruits:List<MenuItemRawModel>,

    @SerializedName("Autres ")
    val autres:List<MenuItemRawModel>,

    @SerializedName("Choix toppings")
    val toppings:List<MenuItemRawModel>,

    @SerializedName("Desserts")
    val desserts:List<MenuItemRawModel>,

    @SerializedName("Boissons")
    val boissons:List<MenuItemRawModel>,

    @SerializedName("Couverts")
    val couverts:List<MenuItemRawModel>,

)