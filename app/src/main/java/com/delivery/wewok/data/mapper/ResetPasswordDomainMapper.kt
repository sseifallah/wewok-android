package com.delivery.wewok.data.mapper

import com.delivery.wewok.base.Mapper
import com.delivery.wewok.base.ext.log
import com.delivery.wewok.base.ext.safeToString
import com.delivery.wewok.data.model.ResetPasswordApiModel
import com.delivery.wewok.data.model.ResetPasswordModel
import com.delivery.wewok.data.model.ZoneApiModel
import com.delivery.wewok.data.model.ZoneModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ResetPasswordDomainMapper @Inject constructor() : Mapper<ResetPasswordApiModel, ResetPasswordModel> {
    override suspend fun invoke(input: ResetPasswordApiModel): ResetPasswordModel =
            withContext(Dispatchers.Default) {
                return@withContext with(input) {
                    var result : ResetPasswordModel? = null
                    try {

                        val data = input.data
                        val status = data?.status!!
                        val message = input.message.safeToString()
                        result = ResetPasswordModel(status,message)
                    } catch (ex: Exception) {
                        this@ResetPasswordDomainMapper.log(ex.message.safeToString())
                    }
                    return@withContext result!!
                }

            }
}
