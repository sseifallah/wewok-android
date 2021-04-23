package fr.mi.wewok.utils.notifications

import androidx.lifecycle.*
import fr.mi.wewok.data.model.*
import fr.mi.wewok.domain.CommandeRepository
import com.mobilemovement.kotlintvmaze.base.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.mi.wewok.domain.NotificationsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
        private val savedStateHandle: SavedStateHandle,
        private val notificationRepository: NotificationsRepository): ViewModel(){


    fun setToken(client_id: Int, token : String)
    {
        viewModelScope.launch(Dispatchers.IO) {
            notificationRepository.setToken(client_id,token)

        }
    }

    fun sendNotifiction(client_id: Int, title : String, message : String){
        viewModelScope.launch(Dispatchers.IO) {
           notificationRepository.sendNotification(client_id,title,message)
        }
    }



    /*fun getCommandes(userId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            commandesResponse= commandeRepository.(userId,total,menuIds)
            commandesLiveData.postValue(commandesResponse)
        }
    }*/
}