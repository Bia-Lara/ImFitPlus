package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.utils.LocalDateConverter

@Database(entities = [DataPerson::class, Calculo::class], version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class AppRoom: RoomDatabase() {
    abstract fun CalculoDao(): CalculoDao
    abstract fun PersonDao(): DataPersonDao
}