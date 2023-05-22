package org.d3if3152.riconvert.model

import org.d3if3152.riconvert.db.KonversiEntity

fun KonversiEntity.convertCurrency(): HasilKonversi {
    val selectedValue = selectedCurrency
    var hasil: Float = 0f;
    if(selectedValue == "Dollar"){
        val dollarRp = 15059
        hasil = nominal.toFloat() / dollarRp

    }else if(selectedValue == "Euro") {
        val euroRp = 16371
        hasil = nominal.toFloat() / euroRp
    }
    return HasilKonversi(hasil)
}
