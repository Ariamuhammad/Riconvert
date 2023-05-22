package org.d3if3152.riconvert.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface KonversiDao {
    @Insert
    fun insert(konversi: KonversiEntity)
    @Query("SELECT * FROM konversi ORDER BY id DESC")
    fun getLastKonversi(): LiveData<List<KonversiEntity?>>
    @Query("DELETE FROM konversi")
    fun clearData()
    @Query("SELECT * FROM konversi ORDER BY id DESC LIMIT 1")
    fun getLastHistoryData(): LiveData<KonversiEntity>


    @Query("DELETE FROM konversi WHERE id = :id")
    fun deleteHistory(id: Long)

    @Query("SELECT * FROM konversi WHERE id = :id")
    fun getHistoryById(id: Long): LiveData<KonversiEntity>
}
