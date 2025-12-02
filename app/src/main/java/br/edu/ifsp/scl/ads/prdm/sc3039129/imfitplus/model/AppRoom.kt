package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DataPerson::class, Calculo::class], version = 1)
abstract class AppRoom: RoomDatabase() {
    abstract fun getCalculoDao(): CalculoDao
    abstract fun getDataPersonDao(): DataPersonDao
}