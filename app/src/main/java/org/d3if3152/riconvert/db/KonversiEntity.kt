package org.d3if3152.riconvert.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "konversi")
data class KonversiEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var tanggal: Long = System.currentTimeMillis(),
    var nominal: String,
    var selectedCurrency: String,
)