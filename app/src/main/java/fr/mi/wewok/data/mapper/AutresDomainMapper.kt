package fr.mi.wewok.data.mapper

import fr.mi.wewok.base.Mapper
import fr.mi.wewok.base.ext.log
import fr.mi.wewok.base.ext.safeToString
import fr.mi.wewok.data.model.AutresModel
import fr.mi.wewok.data.model.AutresRawModel
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