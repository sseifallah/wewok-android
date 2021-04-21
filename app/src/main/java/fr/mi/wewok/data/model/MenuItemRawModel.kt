package fr.mi.wewok.data.model

import com.google.gson.annotations.SerializedName

data class MenuItemRawModel (

    @SerializedName("extra_id")
    val id: String? = null,

    @SerializedName("extra_name")
    val title: String? = null,

    @SerializedName("extra_description")
    val description: String? = null,

    @SerializedName("extra_photo")
    val image: String,

    @SerializedName("extra_price")
    val price: String? = null,

    @SerializedName("ingredients")
    val items: List<Ingredients>? = null,

    var selected : Boolean = false
)

data class Ingredients (
    @SerializedName("group_ingredient_title")
    val ingredientTitle: String? = null,

    @SerializedName("group_ingredient_multiple")
    val ingredientMultiple: Boolean? = false,

    @SerializedName("ingredientMultiple")
    val ingredientRequired:  Boolean? = false,

    @SerializedName("group_ingredient_items")
    val ingredientItems: List<IngredientItem>? = null,

)

data class IngredientItem (
    @SerializedName("ingredient_id")
    val id: String? = null,

    @SerializedName("ingredient_name")
    val name: String? = null,

    @SerializedName("ingredient_price")
    val price:  String? = null,

    @SerializedName("extra_photo")
    val image:  String,

    var selected : Boolean = false
)

