package com.delivery.wewok.data.mapper

import com.delivery.wewok.base.Mapper
import com.delivery.wewok.base.ext.log
import com.delivery.wewok.base.ext.safeToString
import com.delivery.wewok.data.model.BasesModel
import com.delivery.wewok.data.model.RetirerIngredientModel
import com.delivery.wewok.data.model.WoksModel
import com.delivery.wewok.data.model.WoksRawModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WoksDomainMapper @Inject constructor() : Mapper<WoksRawModel, List<WoksModel>> {
    override suspend fun invoke(input: WoksRawModel): List<WoksModel> =
        withContext(Dispatchers.Default) {
            val list = ArrayList<WoksModel>()
            val wok = input.woks
            wok.forEach {
                it?.let { value->
                    value.price?.let {
                        try {
                            val title =  value.title.safeToString()
                            val image  = value.image.safeToString()
                            val description  = value.description.safeToString()
                            val price  = value.price.toFloat()
                            val model =  WoksModel(title,image,description,price)
                            /*value.menuItemExtra?.let {
                                if(it.isJsonObject){
                                    // bases
                                    if(it.asJsonObject.has("0")){
                                        val _0 =  it.asJsonObject.get("0")
                                        if(_0.isJsonObject)
                                        {
                                            val titles = _0.asJsonObject["title"]
                                            if(titles.isJsonArray){
                                                for (title in titles.asJsonArray){
                                                    val base = BasesModel(title.asString,"img_base_1")
                                                    model.bases.add(base)
                                                }
                                            }
                                        }
                                    }
                                    // ingrediant
                                    if(it.asJsonObject.has("1")){
                                        val _1 =  it.asJsonObject.get("1")
                                        if(_1.isJsonObject)
                                        {
                                            val titles = _1.asJsonObject["title"]
                                            if(titles.isJsonArray){
                                                for (title in titles.asJsonArray){
                                                    val retirerIngredient = RetirerIngredientModel(title.asString,"img_retier_ingredient_1")
                                                    model.retirerIngredients.add(retirerIngredient)
                                                }
                                            }
                                        }
                                    }
                                }
                            }*/
                            list.add(model)
                        }catch (ex:Exception){
                            this@WoksDomainMapper.log(ex.message.safeToString())
                        }
                    }
                }
            }
            return@withContext list
        }
}