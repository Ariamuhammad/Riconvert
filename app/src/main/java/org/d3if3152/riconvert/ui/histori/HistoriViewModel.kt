package org.d3if3152.riconvert.ui.histori

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3152.riconvert.db.KonversiDao

class HistoriViewModel(private val db: KonversiDao): ViewModel() {
    val data = db.getLastKonversi()

    fun clearData() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            db.clearData()
        }
    }

    fun hapusData(id: Long) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            db.deleteHistory(id)
        }
    }

}