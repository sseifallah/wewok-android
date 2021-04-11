package com.delivery.wewok.ui.home.home

import androidx.lifecycle.*
import com.delivery.wewok.base.ext.safeToString
import com.delivery.wewok.data.model.*
import com.delivery.wewok.domain.WokRepository
import com.delivery.wewok.utils.*
import com.mobilemovement.kotlintvmaze.base.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.paperdb.Paper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val wokRepository: WokRepository
): ViewModel() {

    var woksResponse : Resource<WoksRawModel>? = null
    val woksLiveData= MutableLiveData<Resource<WoksRawModel>>()


    var zoneResponse : Resource<ZoneModel>? = null
    val zoneLiveData= MutableLiveData<Resource<ZoneModel>>()

    fun getWoks()
    {
        viewModelScope.launch(Dispatchers.IO) {
            woksResponse= wokRepository.getWoks()
            woksLiveData.postValue(woksResponse)
        }
    }


    fun checkZone(zoneCode: String){
        viewModelScope.launch(Dispatchers.IO) {
            zoneResponse= wokRepository.checkZone(zoneCode)
            zoneLiveData.postValue(zoneResponse)
            if (zoneResponse?.data?.status == true){
                Paper.book().write(FRAIS_LIVRAISON, zoneResponse?.data?.frais.safeToString())
                Paper.book().write(ZONE_CHECKED,true)
                Paper.book().write(CODE_ZONE,zoneCode)
            }
            else {
                Paper.book().write(ZONE_CHECKED, false)
                Paper.book().write(CODE_ZONE,zoneCode)
            }
        }
    }



}