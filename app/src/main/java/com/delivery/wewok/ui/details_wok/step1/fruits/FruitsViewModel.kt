package com.delivery.wewok.ui.details_wok.step1.fruits

import androidx.lifecycle.*
import com.delivery.wewok.data.model.FromagesModel
import com.delivery.wewok.domain.WokRepository
import com.mobilemovement.kotlintvmaze.base.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FruitsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val wokRepository: WokRepository
): ViewModel() {

    private val _fromagesLiveData = MutableLiveData<Resource<List<FromagesModel>>>()
    val fromagesLiveData: LiveData<Resource<List<FromagesModel>>> get() = _fromagesLiveData

    private fun getFromages()
    {
        viewModelScope.launch(Dispatchers.IO) {
            _fromagesLiveData.postValue(Resource.loading())
            val fromages = wokRepository.getFromages()
            _fromagesLiveData.postValue(fromages)
        }
    }

    fun setStateEvent(stateEvent : StateEvent)
    {
        when(stateEvent){
            StateEvent.OnStart ->{
                getFromages()
            }
            else -> {

            }
        }
    }

    sealed class StateEvent {
        object OnStart : StateEvent()
    }

}