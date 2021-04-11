package com.delivery.wewok.data.mapper

import com.delivery.wewok.base.Mapper
import com.delivery.wewok.base.ext.log
import com.delivery.wewok.base.ext.safeToString
import com.delivery.wewok.data.model.ConnectModel
import com.delivery.wewok.data.model.ConnectModelR
import com.delivery.wewok.data.model.RegisterModel
import com.delivery.wewok.data.model.RegisterModelR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterDomainMapper @Inject constructor() : Mapper<RegisterModel, RegisterModelR> {
    override suspend fun invoke(input: RegisterModel): RegisterModelR =
            withContext(Dispatchers.Default) {
                return@withContext with(input) {
                    var result : RegisterModelR? = null
                    try {
                        val code = input.code
                        val message = input.message.safeToString()
                        result = RegisterModelR(code,message)
                    } catch (ex: Exception) {
                        this@RegisterDomainMapper.log(ex.message.safeToString())
                    }
                    return@withContext result!!
                }

            }
}