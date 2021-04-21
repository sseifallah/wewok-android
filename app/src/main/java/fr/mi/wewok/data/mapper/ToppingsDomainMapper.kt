package fr.mi.wewok.data.mapper

import fr.mi.wewok.base.Mapper
import fr.mi.wewok.base.ext.log
import fr.mi.wewok.base.ext.safeToString
import fr.mi.wewok.data.model.ToppingsModel
import fr.mi.wewok.data.model.ToppingsRawModel
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