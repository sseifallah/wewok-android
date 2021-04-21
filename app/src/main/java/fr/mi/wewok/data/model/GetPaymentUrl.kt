package fr.mi.wewok.data.model

import com.google.gson.annotations.SerializedName

data class GetPaymentUrl(
    @SerializedName("status")
    var status : Boolean? = null,
    @SerializedName("order_id")
    var order_id : Int? = null,
    @SerializedName("order_id_wc")
    var order_id_wc : Boolean? = null,
    @SerializedName("pay_url")
    var pay_url: String? = null,
    @SerializedName("return_success_url")
    var success_url: String? = null,
    @SerializedName("return_error_url")
    var error_url: String? = null)