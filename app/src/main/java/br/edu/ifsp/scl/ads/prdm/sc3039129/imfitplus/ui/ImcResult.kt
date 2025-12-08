package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.controller.MainController
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.ActivityImcResultBinding
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Calculo
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Constants
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.DataPerson
import kotlin.math.round

class ImcResult : BaseActivity() {
    private val binding: ActivityImcResultBinding by lazy {
        ActivityImcResultBinding.inflate(layoutInflater)
    }

    private val mainController: MainController by lazy {
        MainController(this)
    }
    private var dadosPessoa: DataPerson? = null
    private var userId: Long = -1L
    private var calculoId: Long = -1L
    private var imc: Double = 0.0
    private var categoria: String = ""

    lateinit var uiHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarIn.toolbar)

        dadosPessoa = intent.getParcelableExtra(Constants.EXTRA_DATA_PERSON)
        userId = intent.getLongExtra(Constants.EXTRA_USER_ID, -1L)

        initializeHandler()
        setupClickListeners()

        if (dadosPessoa != null && userId != -1L) {
            imc = calculateImc(dadosPessoa!!.peso, dadosPessoa!!.altura)
            categoria = calculateCategory(imc)
            updateUi(dadosPessoa!!.nome, imc, categoria)

            val novoCalculo = Calculo(
                idUsuario = userId.toInt(),
                nome = dadosPessoa?.nome,
                imc = round(imc * 100) / 100.0,
                categoriaImc = categoria
            )

            mainController.inserirCalculo(novoCalculo)

        } else {
            Toast.makeText(this, "Erro: Dados do usuário não encontrados.", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun initializeHandler() {
        uiHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                if (msg.what == 2) {
                    calculoId = msg.data.getLong("calculoId", -1L)
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.voltarBt.setOnClickListener { finish() }

        binding.calcularGastoCaloricoBt.setOnClickListener {
            if (calculoId == -1L) {
                Toast.makeText(this, "Aguarde, salvando dados...", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this@ImcResult, GastoCalorico::class.java).apply {
                putExtra(Constants.EXTRA_DATA_PERSON, dadosPessoa)
                putExtra(Constants.EXTRA_IMC, imc)
                putExtra(Constants.EXTRA_CATEGORIA, categoria)
                putExtra(Constants.EXTRA_CALCULO_ID, calculoId)
            }
            startActivity(intent)
        }
    }

    private fun updateUi(nome: String?, imc: Double, categoria: String) {
        binding.nomeTv.text = nome
        binding.imcTv.text = String.format("%.2f", imc)
        binding.categoriaTv.text = categoria
    }

    private fun calculateImc(peso: Double, altura: Double): Double {
        if (altura == 0.0) return 0.0
        return peso / (altura * altura)
    }

    private fun calculateCategory(imc: Double): String {
        return when {
            imc < 18.5 -> "Abaixo do Peso"
            imc in 18.5..24.9 -> "Peso Normal"
            imc in 25.0..29.9 -> "Sobrepeso"
            imc >= 30.0 -> "Obesidade"
            else -> "Indefinido"
        }
    }
}
