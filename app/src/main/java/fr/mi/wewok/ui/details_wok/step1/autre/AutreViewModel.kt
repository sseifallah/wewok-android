package fr.mi.wewok.ui.details_wok.step1.autre

import androidx.lifecycle.*
import fr.mi.wewok.data.model.AutresModel
import fr.mi.wewok.domain.WokRepository
import com.mobilemovement.kotlintvmaze.base.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AutreViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val wokRepository: WokRepository
): ViewModel() {
    private val _autresLiveData = MutableLiveData<Resource<List<AutresModel>>>()
    val autreLiveData: LiveData<Resource<List<AutresModel>>> get() = _autresLiveData

    private fun getAutres()
    {
        viewModelScope.launch(Dispatchers.IO) {
            _autresLiveData.postValue(Resource.loading())
            val autres = wokRepository.getAutres()
            _autresLiveData.postValue(autres)
        }
    }

    fun setStateEvent(stateEvent : StateEvent)
    {
        when(stateEvent){
            StateEvent.OnStart ->{
                getAutres()
            }
            else -> {

            }
        }
    }

    sealed class StateEvent {
        object OnStart : StateEvent()
    }
}