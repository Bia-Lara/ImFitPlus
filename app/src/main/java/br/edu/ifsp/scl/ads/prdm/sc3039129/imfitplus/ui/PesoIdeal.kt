package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.controller.MainController
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.ActivityPesoIdealBinding
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Calculo
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Constants
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.DataPerson
import kotlin.math.round

class PesoIdeal : BaseActivity() {
    private val binding: ActivityPesoIdealBinding by lazy {
        ActivityPesoIdealBinding.inflate(layoutInflater)
    }

    private val mainController: MainController by lazy {
        MainController(this)
    }
    private var dados: DataPerson? = null
    private var calculoId: Long = -1L
    private var pesoIdeal: Double = 0.0
    lateinit var uiHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarIn.toolbar)

        dados = intent.getParcelableExtra(Constants.EXTRA_DATA_PERSON)
        calculoId = intent.getLongExtra(Constants.EXTRA_CALCULO_ID, -1L)

        initializeHandler()
        setupClickListeners()

        if (dados != null && calculoId != -1L) {
            mainController.getCalculo(calculoId.toInt())
        } else {
            Toast.makeText(this, "Erro ao carregar dados. Tente novamente.", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun initializeHandler() {
        uiHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                if (msg.what == 1) {
                    val calculo = msg.data.getParcelable<Calculo>("calculo")
                    if (calculo != null && dados != null) {
                        pesoIdeal = calculatePesoIdeal(dados!!.altura)
                        calculo.pesoIdeal = round(pesoIdeal * 100) / 100.0
                        mainController.updateCalculo(calculo)
                        binding.resultadoPesoIdealTv.text = String.format("%.2f", calculo.pesoIdeal)
                    }
                }
            }
        }
    }
    private fun setupClickListeners() {
        binding.voltarBt.setOnClickListener {
            finish()
        }

        binding.resumoBt.setOnClickListener {
            val proximaIntent = Intent(this@PesoIdeal, ResumoSaude::class.java).apply {
                putExtra(Constants.EXTRA_DATA_PERSON, dados)
                putExtra(Constants.EXTRA_IMC, intent.getDoubleExtra(Constants.EXTRA_IMC, 0.0))
                putExtra(Constants.EXTRA_CATEGORIA, intent.getStringExtra(Constants.EXTRA_CATEGORIA))
                putExtra(Constants.EXTRA_GASTO_CALORICO, intent.getDoubleExtra(Constants.EXTRA_GASTO_CALORICO, 0.0))
                putExtra(Constants.EXTRA_PESO_IDEAL, pesoIdeal)
                putExtra(Constants.EXTRA_NOME, dados?.nome)
                putExtra(Constants.EXTRA_PESO, dados?.peso)
            }
            startActivity(proximaIntent)
        }
    }

    private fun calculatePesoIdeal(altura: Double): Double {
        if (altura == 0.0) return 0.0
        return 22 * (altura * altura)
    }
}
