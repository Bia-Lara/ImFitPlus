package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.ActivityResumoSaudeBinding
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Constants

class ResumoSaude : BaseActivity() {
    private val binding: ActivityResumoSaudeBinding by lazy{
        ActivityResumoSaudeBinding.inflate(layoutInflater)
    }

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