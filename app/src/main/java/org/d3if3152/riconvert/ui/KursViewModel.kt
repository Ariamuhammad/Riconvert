package org.d3if3152.riconvert.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.d3if3152.riconvert.model.HasilKonversi
import org.d3if3152.riconvert.model.Kurs

class MainViewModel : ViewModel() {

    private val hasilKonversi = MutableLiveData<HasilKonversi?>()

    private val currencies = listOf(
        Kurs("Dollar", 15059f),
        Kurs("Euro", 16371f)
    )

    fun convertCurrency(nominal: String, selectedCurrency: String): Any {
        val currency = currencies.find { it.name == selectedCurrency }
        return if (currency != null) {
            val hasil = nominal.toFloat() / currency.rate
            hasilKonversi.value = HasilKonversi(hasil)
        } else {
            ""
        }
    }

    fun getHasilKonversi(): LiveData<HasilKonversi?> = hasilKonversi
}
