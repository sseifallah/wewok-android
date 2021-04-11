package com.delivery.wewok.data.mapper

import com.delivery.wewok.base.Mapper
import com.delivery.wewok.base.ext.log
import com.delivery.wewok.base.ext.safeToString
import com.delivery.wewok.data.model.FromagesModel
import com.delivery.wewok.data.model.FromagesRawModel
import com.delivery.wewok.data.model.ProteinesModel
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