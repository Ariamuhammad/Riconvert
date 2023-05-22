package org.d3if3152.riconvert.model

import org.d3if3152.riconvert.db.KonversiEntity

fun KonversiEntity.convertCurrency(): HasilKonversi {
    val hasil = nominal.toFloat() / 15059f

    return HasilKonversi(hasil)
}
