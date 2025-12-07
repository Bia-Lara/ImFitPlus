package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.controller.MainController
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.ui.ResumoSaude
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.ActivityPesoIdealBinding
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Constants
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.DataPerson

class PesoIdeal : BaseActivity() {
    private val binding: ActivityPesoIdealBinding by lazy{
        ActivityPesoIdealBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarIn.toolbar)

        val dados= intent.getParcelableExtra<DataPerson>(Constants.EXTRA_DATA_PERSON)


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
                putExtra(Constants.EXTRA_DATA_PERSON, dados)
                putExtra(Constants.EXTRA_IMC, intent.getDoubleExtra(Constants.EXTRA_IMC,0.0))
                putExtra(Constants.EXTRA_CATEGORIA, intent.getStringExtra(Constants.EXTRA_CATEGORIA))
                putExtra(Constants.EXTRA_GASTO_CALORICO, intent.getDoubleExtra(Constants.EXTRA_GASTO_CALORICO,0.0))
                putExtra(Constants.EXTRA_PESO_IDEAL, pesoIdeal)
                putExtra(Constants.EXTRA_NOME, nome)
                putExtra(Constants.EXTRA_PESO, peso)

            }

            startActivity(intent)
        }
    }
}