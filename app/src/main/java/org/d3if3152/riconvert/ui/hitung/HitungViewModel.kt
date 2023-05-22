package org.d3if3152.riconvert.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3152.riconvert.db.KonversiDao
import org.d3if3152.riconvert.db.KonversiEntity
import org.d3if3152.riconvert.model.HasilKonversi
import org.d3if3152.riconvert.model.Kurs
import org.d3if3152.riconvert.model.convertCurrency

class MainViewModel(private val db: KonversiDao) : ViewModel() {

    private val hasilKonversi = MutableLiveData<HasilKonversi?>()

    private val currencies = listOf(
        Kurs("Dollar", 15059f),
        Kurs("Euro", 16371f)
    )

    fun convertCurrency(nominal: String, selectedCurrency: String) {
        val dataKonversi = KonversiEntity(
            nominal = nominal,
            selectedCurrency = selectedCurrency,
        )
        hasilKonversi.value = dataKonversi.convertCurrency()

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insert(dataKonversi)
            }
        }
    }


    fun getHasilKonversi(): LiveData<HasilKonversi?> = hasilKonversi

    fun getCurrencies(): List<Kurs> = currencies
}