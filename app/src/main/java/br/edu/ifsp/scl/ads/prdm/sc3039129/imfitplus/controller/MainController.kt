package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.controller


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.ui.MainActivity
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.CalculoDao
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.AppRoom
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Calculo
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.DataPerson
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.DataPersonDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainController(private val activity: AppCompatActivity) {

    private val db: AppRoom = Room.databaseBuilder(
        activity.applicationContext,
        AppRoom::class.java,
        "imfitplus-db"
    ).build()

    val calculoDao: CalculoDao = db.CalculoDao()
    val personDao: DataPersonDao = db.PersonDao()
    private val databaseScope = CoroutineScope(Dispatchers.IO)

    fun inserirUsuario(usuario: DataPerson, onResult: (Long) -> Unit) {
        databaseScope.launch {
            val id = personDao.insert(usuario)
            activity.runOnUiThread {
                onResult(id)
            }
        }
    }

    fun inserirCalculo(calculo: Calculo) {
        databaseScope.launch {
            calculoDao.insert(calculo)
        }
    }

    fun buscarHistoricoDoUsuario(usuarioId: Int){
        databaseScope.launch {
            calculoDao.getHistoricoUsuario(usuarioId).let{
                calculoList->
                    //TODO
            }
        }
    }


}
