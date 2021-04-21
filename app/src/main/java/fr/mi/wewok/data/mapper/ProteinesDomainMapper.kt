package fr.mi.wewok.data.mapper

import fr.mi.wewok.base.Mapper
import fr.mi.wewok.base.ext.log
import fr.mi.wewok.base.ext.safeToString
import fr.mi.wewok.data.model.ProteinesModel
import fr.mi.wewok.data.model.ProteinesRawModel
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