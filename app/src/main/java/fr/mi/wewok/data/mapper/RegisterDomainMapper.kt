package fr.mi.wewok.data.mapper

import fr.mi.wewok.base.Mapper
import fr.mi.wewok.base.ext.log
import fr.mi.wewok.base.ext.safeToString
import fr.mi.wewok.data.model.RegisterModel
import fr.mi.wewok.data.model.RegisterModelR
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