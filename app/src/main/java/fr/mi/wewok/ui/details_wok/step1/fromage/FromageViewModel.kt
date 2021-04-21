package fr.mi.wewok.ui.details_wok.step1.fromage

import androidx.lifecycle.*
import fr.mi.wewok.data.model.FromagesModel
import fr.mi.wewok.domain.WokRepository
import com.mobilemovement.kotlintvmaze.base.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FromageViewModel @Inject constructor(
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