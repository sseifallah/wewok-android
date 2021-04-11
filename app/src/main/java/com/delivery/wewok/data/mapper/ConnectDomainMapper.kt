package com.delivery.wewok.data.mapper

import com.delivery.wewok.base.Mapper
import com.delivery.wewok.base.ext.log
import com.delivery.wewok.base.ext.safeToString
import com.delivery.wewok.data.model.BoissonsModel
import com.delivery.wewok.data.model.BoissonsRawModel
import com.delivery.wewok.data.model.ConnectModel
import com.delivery.wewok.data.model.ConnectModelR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConnectDomainMapper  @Inject constructor() : Mapper<ConnectModel, ConnectModelR> {
    override suspend fun invoke(input: ConnectModel): ConnectModelR =
            withContext(Dispatchers.Default) {
                return@withContext with(input) {
                    var result : ConnectModelR? = null
                    try {
                        val code = input.code
                        val status = input.status.safeToString()
                        val user = input.user
                        result = ConnectModelR(status, code, user)
                    } catch (ex: Exception) {
                        this@ConnectDomainMapper.log(ex.message.safeToString())
                    }
                    return@withContext result!!
                }

            }
}
