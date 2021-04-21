package fr.mi.wewok.data.mapper

import fr.mi.wewok.base.Mapper
import fr.mi.wewok.base.ext.log
import fr.mi.wewok.base.ext.safeToString
import fr.mi.wewok.data.model.FromagesModel
import fr.mi.wewok.data.model.FromagesRawModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FromagesDomainMapper @Inject constructor() : Mapper<FromagesRawModel, List<FromagesModel>> {
    override suspend fun invoke(input: FromagesRawModel): List<FromagesModel> =
        withContext(Dispatchers.Default) {
          return@withContext with(input) {
              val list = ArrayList<FromagesModel>()
              val data = input.formages
              data.forEach {
                  it.value?.let {value->
                      value.price?.let {
                          try {
                              val title =  value.title.safeToString()
                              val image  = value.image.safeToString()
                              val price  = value.price.toFloat()
                              val model =  FromagesModel(title,image,price)
                              list.add(model)
                          }catch (ex:Exception){
                              this@FromagesDomainMapper.log(ex.message.safeToString())
                          }
                      }
                  }
              }
              return@withContext list
          }
    }
}