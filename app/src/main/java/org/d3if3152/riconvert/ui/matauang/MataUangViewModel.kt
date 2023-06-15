package org.d3if3152.riconvert.ui.matauang

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3152.riconvert.MataUang
import org.d3if3152.riconvert.network.ApiStatus
import org.d3if3152.riconvert.network.MataUangApi

class MataUangViewModel : ViewModel() {

    private val data = MutableLiveData<List<MataUang>>()
    private val status = MutableLiveData<ApiStatus>()

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO){
            status.postValue(ApiStatus.LOADING)
            try {
                data.postValue(MataUangApi.service.getUang())
                status.postValue(ApiStatus.SUCCES)
            }catch (e: Exception){
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.postValue(ApiStatus.FAILED)
            }
        }
    }

    fun getData(): LiveData<List<MataUang>> = data

    fun getStatus(): LiveData<ApiStatus> = status
}