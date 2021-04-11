package com.delivery.wewok.data.mapper

import com.delivery.wewok.base.Mapper
import com.delivery.wewok.data.model.RetirerIngredientModel
import com.delivery.wewok.data.model.RetirerIngredientRawModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RetirerIngredientMapper @Inject constructor() : Mapper<RetirerIngredientRawModel, RetirerIngredientModel>{
    override suspend fun invoke(input: RetirerIngredientRawModel): RetirerIngredientModel =
        withContext(Dispatchers.Default) {
            return@withContext with(input) {
                // TODO: 06/02/2021 convert RetirerIngredientRawModel to RetirerIngredientModel
                RetirerIngredientModel("default title","default url","","",false)
            }
        }
}