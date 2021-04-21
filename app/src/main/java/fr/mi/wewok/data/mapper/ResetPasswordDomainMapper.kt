package fr.mi.wewok.data.mapper

import fr.mi.wewok.base.Mapper
import fr.mi.wewok.base.ext.log
import fr.mi.wewok.base.ext.safeToString
import fr.mi.wewok.data.model.ResetPasswordApiModel
import fr.mi.wewok.data.model.ResetPasswordModel
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
