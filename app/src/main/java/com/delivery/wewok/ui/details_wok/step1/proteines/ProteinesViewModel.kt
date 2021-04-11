package com.delivery.wewok.ui.details_wok.step1.proteines

import androidx.lifecycle.*
import com.delivery.wewok.data.model.ProteinesModel
import com.delivery.wewok.domain.WokRepository
import com.delivery.wewok.ui.details_wok.step1.proteines.ProteinesViewModel.StateEvent.*
import com.mobilemovement.kotlintvmaze.base.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProteinesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val wokRepository: WokRepository): ViewModel() {

    private val _proteinesLiveData = MutableLiveData<Resource<List<ProteinesModel>>>()
    val proteinesLiveData: LiveData<Resource<List<ProteinesModel>>> get() = _proteinesLiveData

    private fun getProteines()
    {
        viewModelScope.launch(Dispatchers.IO) {
            _proteinesLiveData.postValue(Resource.loading())
            val proteines = wokRepository.getProteines()
            _proteinesLiveData.postValue(proteines)
        }
    }

    fun setStateEvent(stateEvent : StateEvent)
    {
        when(stateEvent){
            OnStart ->{
                getProteines()
            }
            else -> {

            }
        }
    }

    sealed class StateEvent {
        object OnStart : StateEvent()
    }
}