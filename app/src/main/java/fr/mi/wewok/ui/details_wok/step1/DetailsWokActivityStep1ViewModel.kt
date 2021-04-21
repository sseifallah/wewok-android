package fr.mi.wewok.ui.details_wok.step1

import androidx.lifecycle.*
import fr.mi.wewok.data.model.BasesModel
import fr.mi.wewok.data.model.RetirerIngredientModel
import fr.mi.wewok.data.model.ToppingsModel
import fr.mi.wewok.domain.WokRepository
import com.mobilemovement.kotlintvmaze.base.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsWokActivityStep1ViewModel @Inject constructor(
    private val savedStateHandle:SavedStateHandle,
    private val wokRepository: WokRepository): ViewModel(){

    private val _basesLiveData = MutableLiveData<Resource<List<BasesModel>>>()
    val basesLiveData: LiveData<Resource<List<BasesModel>>> get() = _basesLiveData

    private val _retierIngredientsLiveData = MutableLiveData<Resource<List<RetirerIngredientModel>>>()
    val retierIngredientsLiveData: LiveData<Resource<List<RetirerIngredientModel>>> get() = _retierIngredientsLiveData

    private val _toppingsLiveData = MutableLiveData<Resource<List<ToppingsModel>>>()
    val toppingsLiveData: LiveData<Resource<List<ToppingsModel>>> get() = _toppingsLiveData

    private fun getBases() {
      viewModelScope.launch(Dispatchers.IO) {
          _basesLiveData.postValue(Resource.loading())
          val bases = wokRepository.getBases()
          _basesLiveData.postValue(bases)
      }
    }

    private fun getRetirerIngredients() {
        viewModelScope.launch(Dispatchers.IO) {
            _retierIngredientsLiveData.postValue(Resource.loading())
            val retirerIngredients = wokRepository.getRetirerIngredients()
            _retierIngredientsLiveData.postValue(retirerIngredients)
        }
    }

    private fun getToppings()
    {
        viewModelScope.launch(Dispatchers.IO) {
            _toppingsLiveData.postValue(Resource.loading())
            val toppings = wokRepository.getToppings()
            _toppingsLiveData.postValue(toppings)
        }
    }

    fun setStateEvent(stateEvent : StateEvent)
    {
        when(stateEvent){
            StateEvent.OnStart ->{
               getToppings()
            }
            else -> {

           }
        }
    }

    sealed class StateEvent {
        object OnStart : StateEvent()
    }
}