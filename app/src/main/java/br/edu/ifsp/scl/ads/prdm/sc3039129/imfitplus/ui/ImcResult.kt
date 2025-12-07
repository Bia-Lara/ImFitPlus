package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.controller.MainController
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.ActivityImcResultBinding
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Calculo
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Constants
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.DataPerson
import kotlin.math.round

class ImcResult : BaseActivity() {
    private val binding: ActivityImcResultBinding by lazy{
        ActivityImcResultBinding.inflate(layoutInflater)
    }

    private val mainController: MainController by lazy {
        MainController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbarIn.toolbar)

        setContentView(binding.root)

        val dadosPessoa = intent.getParcelableExtra<DataPerson>(Constants.EXTRA_DATA_PERSON)
        val userId = intent.getLongExtra(Constants.EXTRA_USER_ID, -1L)

        fun calculateImc(peso:Double, altura:Double):Double{
            return peso / (altura * altura)
        }

        fun calculateCategory(imc:Double):String{
            return when {
                imc < 18.5 -> "Abaixo do Peso"
                imc >= 18.5 && imc < 25.0 -> "Peso Normal"
                imc >= 25.0 && imc < 30.0 -> "Sobrepeso"
                imc >= 30.0 -> "Obesidade"
                else -> "Indefinido"
            }
        }

        var imc=0.0
        var categoria=""
        var calculoId: Long=-1

        dadosPessoa.let{
            with(binding){
                nomeTv.setText(it?.nome)
                imc= calculateImc(it?.peso!!, it?.altura!!)
                imcTv.setText(String.format("%.2f", imc))
                categoria= calculateCategory(imc)
                categoriaTv.setText(categoria)

            }

            var calculo = Calculo(
                idUsuario = userId.toInt(),
                nome = dadosPessoa?.nome,
                imc = round(imc * 100) / 100.0,
                categoriaImc = categoria
            )

            mainController.inserirCalculo(calculo){
                id->
                calculoId= id

            }
        }

        binding.voltarBt.setOnClickListener { finish() }

        binding.calcularGastoCaloricoBt.setOnClickListener {

            var intent = Intent(this@ImcResult, GastoCalorico::class.java).apply {
                putExtra(Constants.EXTRA_DATA_PERSON, dadosPessoa)
                putExtra(Constants.EXTRA_IMC, imc)
                putExtra(Constants.EXTRA_CATEGORIA, categoria)
                putExtra(Constants.EXTRA_CALCULO_ID, calculoId)
            }
            startActivity(intent)
        }
    }
}