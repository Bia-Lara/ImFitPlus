package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.ActivityImcResultBinding
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.ActivityResumoSaudeBinding
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Constants.EXTRA_CATEGORIA
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Constants.EXTRA_GASTO_CALORICO
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Constants.EXTRA_IMC
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Constants.EXTRA_NOME
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Constants.EXTRA_PESO
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Constants.EXTRA_PESO_IDEAL

class ResumoSaude : AppCompatActivity() {
    private val binding: ActivityResumoSaudeBinding by lazy{
        ActivityResumoSaudeBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        var nome= intent.getStringExtra(EXTRA_NOME)
        var imc = intent.getDoubleExtra(EXTRA_IMC,0.0)
        var categoria= intent.getStringExtra(EXTRA_CATEGORIA)
        var pesoIdeal= intent.getDoubleExtra(EXTRA_PESO_IDEAL, 0.0)
        var gastoCalorico = intent.getDoubleExtra(EXTRA_GASTO_CALORICO, 0.0)
        var peso = intent.getDoubleExtra(EXTRA_PESO,0.0)


        binding.nomeTv.setText(nome)
        binding.imcTv.setText(String.format("%.2f", imc))
        binding.categoriaTv.setText(categoria)
        binding.pesoIdealTv.setText(String.format("%.2f", imc))
        binding.pesoIdealTv.setText(String.format("%.2f", pesoIdeal))
        binding.gastoCaloricoDiarioTv.setText(String.format("%.2f", gastoCalorico))
        binding.recomendacaoAgua.setText(String.format("%.2f", (peso * 350) / 100))


    }
}