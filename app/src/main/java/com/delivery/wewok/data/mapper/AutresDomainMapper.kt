package com.delivery.wewok.data.mapper

import com.delivery.wewok.base.Mapper
import com.delivery.wewok.base.ext.log
import com.delivery.wewok.base.ext.safeToString
import com.delivery.wewok.data.model.AutresModel
import com.delivery.wewok.data.model.AutresRawModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AutresDomainMapper @Inject constructor() : Mapper<AutresRawModel, List<AutresModel>> {
    override suspend fun invoke(input: AutresRawModel): List<AutresModel> =
        withContext(Dispatchers.Default) {
            val list = ArrayList<AutresModel>()
            val data = input.autres
            data.forEach {
                it.value?.let {value->
                    value.price?.let {
                        try {
                            val title =  value.title.safeToString()
                            val image  = value.image.safeToString()
                            val price  = value.price.toFloat()
                            val model =  AutresModel(title,image,price)
                            list.add(model)
                        }catch (ex:Exception){
                            this@AutresDomainMapper.log(ex.message.safeToString())
                        }
                    }
                }
            }
            return@withContext list
        }
}