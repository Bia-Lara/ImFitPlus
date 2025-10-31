package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.ActivityMainBinding
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.ActivityPesoIdealBinding
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Constants.EXTRA_CATEGORIA
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Constants.EXTRA_DATA_PERSON
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Constants.EXTRA_GASTO_CALORICO
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Constants.EXTRA_IMC
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Constants.EXTRA_NOME
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Constants.EXTRA_PESO
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Constants.EXTRA_PESO_IDEAL
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.DataPerson

class PesoIdeal : AppCompatActivity() {
    private val binding: ActivityPesoIdealBinding by lazy{
        ActivityPesoIdealBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val dados= intent.getParcelableExtra<DataPerson>(EXTRA_DATA_PERSON)

        fun calculatePesoIdeal(altura:Double):Double{
            return 22 * (altura*altura)
        }

        var pesoIdeal=0.0
        var nome: String? =""
        var peso: Double? =0.0
        dados.let{
            nome= it?.nome
            peso= it?.peso
            with(binding){

                pesoIdeal= calculatePesoIdeal(it?.altura!!)
                resultadoPesoIdealTv.setText(String.format("%.2f", pesoIdeal))
            }
        }

        binding.voltarBt.setOnClickListener {
            finish()
        }

        binding.resumoBt.setOnClickListener {
            val intent = Intent(this@PesoIdeal, ResumoSaude::class.java).apply{
                putExtra(EXTRA_DATA_PERSON, dados)
                putExtra(EXTRA_IMC, intent.getDoubleExtra(EXTRA_IMC,0.0))
                putExtra(EXTRA_CATEGORIA, intent.getStringExtra(EXTRA_CATEGORIA))
                putExtra(EXTRA_GASTO_CALORICO, intent.getDoubleExtra(EXTRA_GASTO_CALORICO,0.0))
                putExtra(EXTRA_PESO_IDEAL, pesoIdeal)
                putExtra(EXTRA_NOME, nome)
                putExtra(EXTRA_PESO, peso)

            }

            startActivity(intent)
        }
    }
}