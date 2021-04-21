package fr.mi.wewok.data.mapper

import fr.mi.wewok.base.Mapper
import fr.mi.wewok.base.ext.log
import fr.mi.wewok.base.ext.safeToString
import fr.mi.wewok.data.model.ConnectModel
import fr.mi.wewok.data.model.ConnectModelR
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
