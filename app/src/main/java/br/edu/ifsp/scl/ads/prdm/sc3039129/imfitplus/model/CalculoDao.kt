package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@androidx.room.Dao
interface CalculoDao {
    @Insert
    fun insert(calculo: Calculo): Long

    @Update
    fun update(calculo: Calculo): Int

    @Query("SELECT * FROM Calculo")
    fun getAllCalculos(): MutableList<Calculo>

    @Query("SELECT * FROM Calculo WHERE id=:id")
    fun getCalculoById(id: Int): Calculo

    @Query("SELECT * FROM Calculo WHERE idUsuario = :usuarioId ORDER BY dataHora DESC")
    suspend fun getHistoricoUsuario(usuarioId: Int): MutableList<Calculo>

    @Delete
    fun delete(calculo: Calculo): Int



}