package com.delivery.wewok.ui.details_wok.step3

import androidx.lifecycle.*
import com.delivery.wewok.data.model.BoissonsModel
import com.delivery.wewok.data.model.DessertsModel
import com.delivery.wewok.domain.WokRepository
import com.mobilemovement.kotlintvmaze.base.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsWokActivityStep3ViewModel @Inject constructor(
        private val savedStateHandle: SavedStateHandle,
        private val wokRepository: WokRepository): ViewModel(){

    private val _dessertsLiveData = MutableLiveData<Resource<List<DessertsModel>>>()
    val dessertsLiveData: LiveData<Resource<List<DessertsModel>>> get() = _dessertsLiveData

    fun getDesserts()
    {
        viewModelScope.launch(Dispatchers.IO) {
            _dessertsLiveData.postValue(Resource.loading())
            val desserts = wokRepository.getDesserts()
            _dessertsLiveData.postValue(desserts)
        }
    }
}