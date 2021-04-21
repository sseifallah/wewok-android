package fr.mi.wewok.ui.details_wok.commande

import androidx.lifecycle.*
import fr.mi.wewok.data.model.*
import fr.mi.wewok.domain.CommandeRepository
import com.mobilemovement.kotlintvmaze.base.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommandesViewModel @Inject constructor(
        private val savedStateHandle: SavedStateHandle,
        private val commandeRepository: CommandeRepository): ViewModel(){

    var saveResponse : Resource<SaveResponse>? = null
    val saveLiveData= MutableLiveData<Resource<SaveResponse>>()

    var urlResponse : Resource<GetPaymentUrl>? = null
    val urlLiveData= MutableLiveData<Resource<GetPaymentUrl>>()

    var updateResponse : Resource<UpdateResponse>? = null
    val updateLiveData= MutableLiveData<Resource<UpdateResponse>>()

    fun saveOrder(client_adress: String, client_city: String, client_country: String, client_phone: String, client_postcode : String,client_state: String,
                  userId: Int, total: String, menuIds: ArrayList<String>)
    {
        viewModelScope.launch(Dispatchers.IO) {
            saveResponse= commandeRepository.saveOrder(client_adress, client_city, client_country, client_phone, client_postcode,client_state,
                    userId, total, menuIds)
            saveLiveData.postValue(saveResponse)

        }
    }

    fun getPaymentUrl(order_Id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            urlResponse= commandeRepository.getPaymentUrl(order_Id)
            urlLiveData.postValue(urlResponse)
        }
    }

    fun updateStatus(order_Id : Int, status : String){
        viewModelScope.launch(Dispatchers.IO) {
            updateResponse= commandeRepository.updatestatus(order_Id,status)
            updateLiveData.postValue(updateResponse)
        }
    }

    /*fun getCommandes(userId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            commandesResponse= commandeRepository.(userId,total,menuIds)
            commandesLiveData.postValue(commandesResponse)
        }
    }*/
}