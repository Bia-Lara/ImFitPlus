package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.ActivityImcResultBinding
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Constants
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.DataPerson

class ImcResult : BaseActivity() {
    private val binding: ActivityImcResultBinding by lazy{
        ActivityImcResultBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbarIn.toolbar)

        setContentView(binding.root)

        val dadosPessoa = intent.getParcelableExtra<DataPerson>(Constants.EXTRA_DATA_PERSON)

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
        dadosPessoa.let{
            with(binding){
                nomeTv.setText(it?.nome)
                imc= calculateImc(it?.peso!!, it?.altura!!)
                imcTv.setText(String.format("%.2f", imc))
                categoria= calculateCategory(imc)
                categoriaTv.setText(categoria)
            }
        }

        binding.voltarBt.setOnClickListener { finish() }

        binding.calcularGastoCaloricoBt.setOnClickListener {

            var intent = Intent(this@ImcResult, GastoCalorico::class.java).apply {
                putExtra(Constants.EXTRA_DATA_PERSON, dadosPessoa)
                putExtra(Constants.EXTRA_IMC, imc)
                putExtra(Constants.EXTRA_CATEGORIA, categoria)
            }
            startActivity(intent)
        }
    }
}