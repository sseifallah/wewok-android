package fr.mi.wewok.data.mapper

import fr.mi.wewok.base.Mapper
import fr.mi.wewok.base.ext.log
import fr.mi.wewok.base.ext.safeToString
import fr.mi.wewok.data.model.BoissonsModel
import fr.mi.wewok.data.model.BoissonsRawModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BoissonsDomainMapper @Inject constructor() : Mapper<BoissonsRawModel, List<BoissonsModel>> {
    override suspend fun invoke(input: BoissonsRawModel): List<BoissonsModel> =
            withContext(Dispatchers.Default) {
                return@withContext with(input) {
                    val list = ArrayList<BoissonsModel>()
                    val data = input.boissons
                    data.forEach {
                        it.value?.let {value->
                            value.price?.let {
                                try {
                                    val title =  value.title.safeToString()
                                    val image  = value.image.safeToString()
                                    val price  = value.price.toFloat()
                                    val model =  BoissonsModel(title,image,price)
                                    list.add(model)
                                }catch (ex:Exception){
                                    this@BoissonsDomainMapper.log(ex.message.safeToString())
                                }
                            }
                        }
                    }
                    return@withContext list
                }
            }
}