package com.delivery.wewok.data.mapper

import com.delivery.wewok.base.Mapper
import com.delivery.wewok.base.ext.log
import com.delivery.wewok.base.ext.safeToString
import com.delivery.wewok.data.model.RegisterModel
import com.delivery.wewok.data.model.RegisterModelR
import com.delivery.wewok.data.model.ZoneApiModel
import com.delivery.wewok.data.model.ZoneModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ZoneDomainMapper @Inject constructor() : Mapper<ZoneApiModel, ZoneModel> {
    override suspend fun invoke(input: ZoneApiModel): ZoneModel =
            withContext(Dispatchers.Default) {
                return@withContext with(input) {
                    var result : ZoneModel? = null
                    try {

                        val status = input.status
                        val frais = input.frais.safeToString()
                        val message = input.error.safeToString()
                        result = ZoneModel(status,frais,message)
                    } catch (ex: Exception) {
                        this@ZoneDomainMapper.log(ex.message.safeToString())
                    }
                    return@withContext result!!
                }

            }
}
