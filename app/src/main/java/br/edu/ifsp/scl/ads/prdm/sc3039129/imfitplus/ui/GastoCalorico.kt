package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.controller.MainController
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.ActivityGastoCaloricoBinding
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Calculo
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Constants
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.DataPerson
import kotlin.math.round

class GastoCalorico : BaseActivity() {
    private val binding: ActivityGastoCaloricoBinding by lazy {
        ActivityGastoCaloricoBinding.inflate(layoutInflater)
    }

    private val mainController: MainController by lazy {
        MainController(this)
    }
    private var calculoId: Long = -1L
    private var dados: DataPerson? = null
    private var tmbAtual: Double = 0.0
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
                        val tmb = calculateGastoCalorico(dados!!.sexo, dados!!.peso, dados!!.altura, dados!!.idade)
                        tmbAtual = tmb

                        calculo.tmb = round(tmb * 100) / 100.0

                        mainController.updateCalculo(calculo)
                        binding.resultadoGastoCaloricoTv.text = String.format("%.2f", calculo.tmb)
                    }
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.voltarBt.setOnClickListener { finish() }

        binding.calcularPesoIdealBt.setOnClickListener {

            val proximaIntent = Intent(this@GastoCalorico, PesoIdeal::class.java).apply {
                putExtra(Constants.EXTRA_DATA_PERSON, dados)
                putExtra(Constants.EXTRA_GASTO_CALORICO, tmbAtual)
                putExtra(Constants.EXTRA_IMC, intent.getDoubleExtra(Constants.EXTRA_IMC, 0.0))
                putExtra(Constants.EXTRA_CATEGORIA, intent.getStringExtra(Constants.EXTRA_CATEGORIA))
                putExtra(Constants.EXTRA_CALCULO_ID, calculoId)
            }
            startActivity(proximaIntent)
        }
    }

    private fun calculateGastoCalorico(sexo: String, peso: Double, altura: Double, idade: Int): Double {
        return when (sexo) {
            "Feminino" -> 655 + (9.6 * peso) + (1.8 * altura * 100) - (4.7 * idade)
            "Masculino" -> 66 + (13.7 * peso) + (5 * altura * 100) - (6.8 * idade)
            else -> 0.0
        }
    }
}
