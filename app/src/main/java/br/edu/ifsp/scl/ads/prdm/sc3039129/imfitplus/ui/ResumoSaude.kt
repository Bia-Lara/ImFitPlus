package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.controller.MainController
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.ActivityResumoSaudeBinding
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Calculo
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Constants
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.DataPerson
import kotlin.math.round

class ResumoSaude : BaseActivity() {
    private val binding: ActivityResumoSaudeBinding by lazy{
        ActivityResumoSaudeBinding.inflate(layoutInflater)
    }

    private val mainController: MainController by lazy {
        MainController(this)
    }

    lateinit var uiHandler: Handler
    private var dados: DataPerson? = null
    private var calculoId: Long = -1L
    private var zona_leve: Int = 0
    private var zona_queima_gordura: Int = 0
    private var zona_aerobica: Int = 0
    private var zona_anaerobica: Int = 0
    private var fcmax: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarIn.toolbar)

        var nome= intent.getStringExtra(Constants.EXTRA_NOME)
        var imc = intent.getDoubleExtra(Constants.EXTRA_IMC,0.0)
        var categoria= intent.getStringExtra(Constants.EXTRA_CATEGORIA)
        var pesoIdeal= intent.getDoubleExtra(Constants.EXTRA_PESO_IDEAL, 0.0)
        var gastoCalorico = intent.getDoubleExtra(Constants.EXTRA_GASTO_CALORICO, 0.0)
        var peso = intent.getDoubleExtra(Constants.EXTRA_PESO,0.0)

        dados = intent.getParcelableExtra(Constants.EXTRA_DATA_PERSON)
        calculoId = intent.getLongExtra(Constants.EXTRA_CALCULO_ID, -1L)

        initializeHandler()
        setupClickListeners(nome!!, imc, categoria!!, peso, pesoIdeal, gastoCalorico)

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
                        fcmax = calculate_frequencia_cardiaca(dados!!.idade)
                        zona_leve = zona_leve(fcmax)
                        zona_queima_gordura = zona_queima_gordura(fcmax)
                        zona_aerobica = zona_aerobica(fcmax)
                        zona_anaerobica = zona_anaerobica(fcmax)
                        calculo.fcmax = fcmax
                        calculo.zona_leve = zona_leve
                        calculo.zona_queima_gordura = zona_queima_gordura
                        calculo.zona_aerobica = zona_aerobica
                        calculo.zona_anaerobica = zona_anaerobica
                        mainController.updateCalculo(calculo)

                        binding.frequenciaCardiacaTv.text = "Frequência Cardiaca máxima: "+ calculo.fcmax
                        binding.zonaLeve.text= "Zona Leve: " + calculo.zona_leve
                        binding.zonaQueimaGordura.text = "Zona Queima de gordura"+ calculo.zona_queima_gordura
                        binding.zonaAerobica.text = "Zona aeróbica: " + calculo.zona_aerobica
                        binding.zonaAnaerobica.text = "Zona anaerobica: " + calculo.zona_anaerobica

                    }
                }
            }
        }
    }


    private fun calculate_frequencia_cardiaca(idade: Int): Int{
        return 220 - idade
    }

    private fun zona_leve(fcmax: Int): Int{
        var pt1= (fcmax / 50) * 100
        var pt2 = (fcmax / 60) * 100

        return pt1 + pt2 / 2
    }

    private fun zona_queima_gordura(fcmax:Int): Int{
        var pt1= (fcmax / 60) * 100
        var pt2 = (fcmax / 70) * 100

        return pt1 + pt2/2
    }

    private fun zona_aerobica(fcmax: Int): Int{
        var pt1= (fcmax / 70) * 100
        var pt2 = (fcmax / 80) * 100

        return pt1 + pt2/2
    }

    private fun zona_anaerobica(fcmax: Int): Int{
        var pt1= (fcmax / 80) * 100
        var pt2 = (fcmax / 90) * 100

        return pt1 + pt2/2
    }


    private fun setupClickListeners(nome:String, imc: Double, categoria: String, peso: Double, pesoIdeal:Double, gastoCalorico:Double) {
        binding.nomeTv.setText("Nome: " + nome)
        binding.imcTv.setText("IMC: "+ String.format("%.2f", imc))
        binding.categoriaTv.setText("Categoria: "+ categoria)
        binding.pesoTv.setText("Peso: "+ String.format("%.2f", peso))
        binding.pesoIdealTv.setText("Peso Ideal: " + String.format("%.2f", pesoIdeal))
        binding.gastoCaloricoDiarioTv.setText("Gasto Calórico Diário: " + String.format("%.2f", gastoCalorico))
        binding.recomendacaoAgua.setText("Recomendação de Litros de água p/ dia: " + String.format("%.2f", (peso * 35) / 1000))

        binding.voltarBt.setOnClickListener { finish() }
    }
}