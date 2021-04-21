/*
 * Copyright 2019 nuhkoca.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.mi.wewok.data.mapper

import fr.mi.wewok.base.Mapper
import fr.mi.wewok.data.model.BasesModel
import fr.mi.wewok.data.model.BasesRawModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BasesDomainMapper @Inject constructor() : Mapper<BasesRawModel, BasesModel> {
    override suspend fun invoke(input: BasesRawModel): BasesModel =
        withContext(Dispatchers.Default) {
        return@withContext with(input) {
            // TODO: 06/02/2021 convert BasesRawModel to BasesModel
            BasesModel("default title","default url","kj","dkd",false)
        }
    }
}
