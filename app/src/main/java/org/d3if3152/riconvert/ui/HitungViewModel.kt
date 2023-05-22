package org.d3if3152.riconvert.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.d3if3152.riconvert.model.HasilKonversi
import org.d3if3152.riconvert.model.Kurs

class MainViewModel : ViewModel() {
    private val hasilKonversi = MutableLiveData<HasilKonversi?>()
    private val selectedCurrency = MutableLiveData<String>()

    private val currencies = listOf(
        Kurs("Dollar", 15059f),
        Kurs("Euro", 16371f)
    )

    fun convertCurrency(nominal: String, selectedCurrency: String) {
        val currency = currencies.find { it.name == selectedCurrency }
        val hasil = currency?.let {
            nominal.toFloat() / it.rate
        }
        hasilKonversi.value = hasil?.let { HasilKonversi(it) }
    }


    fun getHasilKonversi(): LiveData<HasilKonversi?> = hasilKonversi

    fun setSelectedCurrency(currency: String) {
        selectedCurrency.value = currency
    }

    fun getSelectedCurrency(): LiveData<String> = selectedCurrency

    fun getCurrencies(): List<Kurs> = currencies
}