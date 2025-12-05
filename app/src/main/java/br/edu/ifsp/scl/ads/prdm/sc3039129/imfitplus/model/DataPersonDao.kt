package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@androidx.room.Dao
interface DataPersonDao {
    @Insert
    fun insert(dataPerson: DataPerson): Long

    @Update
    fun update(dataPerson: DataPerson): Int

    @Query("SELECT * FROM DataPerson")
    fun getAllDataPerson(): MutableList<DataPerson>

    @Query("SELECT * FROM DataPerson WHERE id=:id")
    fun getDataPersonById(id: Int): DataPerson

    @Delete
    fun delete(dataPerson: DataPerson): Int
}