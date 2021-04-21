package fr.mi.wewok.ui.details_wok.step2

import androidx.lifecycle.*
import fr.mi.wewok.data.model.BoissonsModel
import fr.mi.wewok.domain.WokRepository
import com.mobilemovement.kotlintvmaze.base.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsWokActivityStep2ViewModel @Inject constructor(
        private val savedStateHandle: SavedStateHandle,
        private val wokRepository: WokRepository): ViewModel(){

    private val _boissonsLiveData = MutableLiveData<Resource<List<BoissonsModel>>>()
    val boissonsLiveData: LiveData<Resource<List<BoissonsModel>>> get() = _boissonsLiveData

    fun getBoissons()
    {
        viewModelScope.launch(Dispatchers.IO) {
            _boissonsLiveData.postValue(Resource.loading())
            val boissons = wokRepository.getBoissons()
            _boissonsLiveData.postValue(boissons)
        }
    }
}