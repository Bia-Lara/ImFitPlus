package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model

import androidx.room.Database

@Database(entities = [DataPerson::class], version = 1)
abstract class DataPersonRoom {
    abstract fun getDataPersonDao(): DataPersonDao
}

