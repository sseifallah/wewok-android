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
package fr.mi.wewok.base

import android.content.Context
import fr.mi.wewok.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DefaultErrorFactory @Inject constructor(@ApplicationContext val context: Context) : ErrorFactory {

    override fun createApiErrorMessage(e: Exception): String = e.message.toString()

    override fun createApiCodeMessage(code: Int): String = code.toString()

    override fun createNoInternetConnectionMessage(): String = context.getString(R.string.Veuillez_vérifier_votre_connexion_Internet)
}
