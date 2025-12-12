
package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.controller
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.AppRoom
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Calculo
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.CalculoDao
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.DataPerson
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.DataPersonDao
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.ui.ImcResult
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.ui.GastoCalorico
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.ui.HistoricoActivity
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.ui.PersonalData
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.ui.PesoIdeal
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.ui.ResumoSaude
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class MainController(private val activity: AppCompatActivity) {

    private val db: AppRoom = Room.databaseBuilder(
        activity.applicationContext,
        AppRoom::class.java,
        "imfitplus-db"
    ).build()

    private val calculoDao: CalculoDao = db.CalculoDao()
    private val personDao: DataPersonDao = db.PersonDao()
    private val databaseScope = CoroutineScope(Dispatchers.IO)
    fun inserirCalculo(calculo: Calculo) {
        databaseScope.launch {
            val novoId: Long = calculoDao.insert(calculo)
            val handler = when (activity) {
                is ImcResult -> activity.uiHandler
                else -> null
            }

            handler?.apply {
                sendMessage(obtainMessage().apply {
                    what = 2
                    data = Bundle().apply {
                        putLong("calculoId", novoId)
                    }
                })
            }
        }
    }

    fun getCalculo(id: Int) {
        databaseScope.launch {
            val calculoEncontrado = calculoDao.getCalculoById(id)

            val handler = when (activity) {
                is ImcResult -> activity.uiHandler
                is GastoCalorico -> activity.uiHandler
                is PesoIdeal -> activity.uiHandler
                is ResumoSaude -> activity.uiHandler
                else -> null
            }

            handler?.apply {
                sendMessage(obtainMessage().apply {
                    what = 1
                    data = Bundle().apply {
                        putParcelable("calculo", calculoEncontrado)
                    }
                })
            }
        }
    }

    fun updateCalculo(calculo: Calculo) {
        databaseScope.launch {
            calculoDao.update(calculo)
        }
    }

    fun inserirUsuario(usuario: DataPerson, onResult: (Long) -> Unit) {
        databaseScope.launch {
            val id = personDao.insert(usuario)
            activity.runOnUiThread {
                onResult(id)
            }
        }
    }


    fun getUltimoUsuario() {
        databaseScope.launch {
            val ultimoUsuario = personDao.getUltimoUsuario()

            val handler = when (activity) {
                is PersonalData -> activity.uiHandler
                else -> null
            }

            handler?.apply {
                sendMessage(obtainMessage().apply {
                    what = 3

                    data = Bundle().apply {
                        putParcelable("ultimoUsuario", ultimoUsuario)
                    }
                })
            }
        }
    }


    fun buscarCalculos() {
        databaseScope.launch {
            val listaDeCalculos = calculoDao.getAllCalculos()

            val handler = when (activity) {
                is HistoricoActivity -> activity.uiHandler
                else -> null
            }

            handler?.apply {
                sendMessage(obtainMessage().apply {
                    what = HistoricoActivity.ATUALIZAR_LISTA_DE_CALCULOS

                    data = Bundle().apply {
                        putParcelableArrayList("listaCalculos", ArrayList(listaDeCalculos))
                    }
                })
            }
        }
    }

}