package com.delivery.wewok.data.mapper

import android.util.Log
import com.delivery.wewok.base.Mapper
import com.delivery.wewok.base.ext.log
import com.delivery.wewok.base.ext.safeToString
import com.delivery.wewok.data.model.ProteinesModel
import com.delivery.wewok.data.model.ProteinesRawModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProteinesDomainMapper @Inject constructor() : Mapper<ProteinesRawModel, List<ProteinesModel>> {
    override suspend fun invoke(input: ProteinesRawModel): List<ProteinesModel> =
        withContext(Dispatchers.Default) {
            val list = ArrayList<ProteinesModel>()
            val data = input.mesGarnitures
            data.forEach {
                it.value?.let {value->
                    value.price?.let {
                        try {
                            val title =  value.title.safeToString()
                            val image  = value.image.safeToString()
                            val price  = value.price.toFloat()
                           val model =  ProteinesModel(title,image,price)
                           list.add(model)
                        }catch (ex:Exception){
                            this@ProteinesDomainMapper.log(ex.message.safeToString())
                        }
                    }
                }
            }
            return@withContext list
        }
}