package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model

import androidx.room.Database

@Database(entities = [Calculo::class], version = 1)
abstract class CalculoRoom {
    abstract fun getCalculoDao(): CalculoDao
}