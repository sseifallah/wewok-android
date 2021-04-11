package com.delivery.wewok.data.mapper

import com.delivery.wewok.base.Mapper
import com.delivery.wewok.base.ext.log
import com.delivery.wewok.base.ext.safeToString
import com.delivery.wewok.data.model.BoissonsModel
import com.delivery.wewok.data.model.BoissonsRawModel
import com.delivery.wewok.data.model.DessertsModel
import com.delivery.wewok.data.model.DessertsRawModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DessertsDomainMapper @Inject constructor() : Mapper<DessertsRawModel, List<DessertsModel>> {
    override suspend fun invoke(input: DessertsRawModel): List<DessertsModel> =
            withContext(Dispatchers.Default) {
                return@withContext with(input) {
                    val list = ArrayList<DessertsModel>()
                    val data = input.desserts
                    data.forEach {
                        it.value?.let {value->
                            value.price?.let {
                                try {
                                    val title =  value.title.safeToString()
                                    val image  = value.image.safeToString()
                                    val price  = value.price.toFloat()
                                    val model =  DessertsModel(title,image,price)
                                    list.add(model)
                                }catch (ex:Exception){
                                    this@DessertsDomainMapper.log(ex.message.safeToString())
                                }
                            }
                        }
                    }
                    return@withContext list
                }
            }
}