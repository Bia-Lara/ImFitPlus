package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.controller

import androidx.room.Room
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.MainActivity
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.CalculoDao
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.AppRoom
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.DataPersonDao

class MainController(val mainActivity: MainActivity) {

    private val db: AppRoom = Room.databaseBuilder(
        mainActivity,
        AppRoom::class.java,
        "imfitplus-db"
    ).build()

    val personDao: DataPersonDao = db.getDataPersonDao()
    val calculoDao: CalculoDao = db.getCalculoDao()



}
