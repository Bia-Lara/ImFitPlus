package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.ActivityImcResultBinding
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Constants.EXTRA_DATA_PERSON
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.DataPerson

class ImcResult : AppCompatActivity() {
    private val binding: ActivityImcResultBinding by lazy{
        ActivityImcResultBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        val dadosPessoa = intent.getParcelableExtra<DataPerson>(EXTRA_DATA_PERSON)

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

        dadosPessoa.let{
            with(binding){
                nomeTv.setText(it?.nome)
                val imc= calculateImc(it?.peso!!, it?.altura!!)
                imcTv.setText(String.format("%.2f", imc))
                categoriaTv.setText(calculateCategory(imc))
            }
        }

        binding.voltarBt.setOnClickListener { finish() }

        binding.calcularGastoCaloricoBt.setOnClickListener {
            var intent = Intent(this@ImcResult, GastoCalorico::class.java).apply {
                putExtra(EXTRA_DATA_PERSON, dadosPessoa)
            }
            startActivity(intent)
        }
    }
}