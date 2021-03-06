package com.delivery.wewok.data.mapper

import com.delivery.wewok.base.Mapper
import com.delivery.wewok.base.ext.log
import com.delivery.wewok.base.ext.safeToString
import com.delivery.wewok.data.model.ProteinesModel
import com.delivery.wewok.data.model.ToppingsModel
import com.delivery.wewok.data.model.ToppingsRawModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ToppingsDomainMapper @Inject constructor() : Mapper<ToppingsRawModel, List<ToppingsModel>>{
    override suspend fun invoke(input: ToppingsRawModel): List<ToppingsModel> =
    withContext(Dispatchers.Default) {
        val list = ArrayList<ToppingsModel>()
        val data = input.toppings
        data.forEach {
            it.value?.let {value->
                value.price?.let {
                    try {
                        val title =  value.title.safeToString()
                        val image  = value.image.safeToString()
                        val price  = value.price.toFloat()
                        val model =  ToppingsModel(title,image,price)
                        list.add(model)
                    }catch (ex:Exception){
                        this@ToppingsDomainMapper.log(ex.message.safeToString())
                    }
                }
            }
        }
        return@withContext list
    }
}