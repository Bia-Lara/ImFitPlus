package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.adapter.HistoryAdapter
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.ActivityHistoricoBinding
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Calculo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HistoricoActivity : AppCompatActivity() {
    private val historyBiding: ActivityHistoricoBinding by lazy {
        ActivityHistoricoBinding.inflate(layoutInflater)
    }

    private val historyList: MutableList<Calculo> = mutableListOf()

    private val historyAdapter: HistoryAdapter by lazy{
        HistoryAdapter(this, historyList )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(historyBiding.root)

        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        historyList.add(Calculo(id = 1, idUsuario = 1, nome = "João", dataHora = LocalDate.parse("01/12/2025", formatter), imc = 24.5, categoriaImc = "Normal", tmb = 1800.0, pesoIdeal = 70.0))
        historyList.add(Calculo(id = 2, idUsuario = 1, nome = "João", dataHora = LocalDate.parse("01/12/2025", formatter), imc = 25.8, categoriaImc = "Sobrepeso", tmb = 1850.0, pesoIdeal = 72.5))
        historyList.add(Calculo(id = 3, idUsuario = 1, nome = "João", dataHora = LocalDate.parse("01/12/2025", formatter), imc = 26.2, categoriaImc = "Sobrepeso", tmb = 1870.0, pesoIdeal = 73.0))

        historyBiding.historyList.adapter = historyAdapter
    }
}