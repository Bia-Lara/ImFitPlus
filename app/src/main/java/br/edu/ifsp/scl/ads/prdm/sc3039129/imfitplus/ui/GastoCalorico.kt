package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.ui.PesoIdeal
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.ActivityGastoCaloricoBinding
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Constants
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.DataPerson

class GastoCalorico : BaseActivity() {
    private val binding: ActivityGastoCaloricoBinding by lazy{
        ActivityGastoCaloricoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarIn.toolbar)


        var dados= intent.getParcelableExtra<DataPerson>(Constants.EXTRA_DATA_PERSON)

        fun calculateGastoCalorico(sexo:String, peso:Double, altura:Double, idade:Int): Double{

            when(sexo){
                "Feminino"->{ return 655 + (9.6*peso) + (1.8*altura*100) - (4.7 * idade) }

                "Masculino"->{ return 66 + (13.7 *peso) + (5*altura*100) - (6.8 * idade) }
            }

            return 0.0
        }

        var tmb=0.0

        dados.let{
            with(binding){
                val sexo= it?.sexo
                val peso= it?.peso
                val altura = it?.altura
                val idade= it?.idade

                tmb = calculateGastoCalorico(sexo!!, peso!!, altura!!, idade!!)

                resultadoGastoCaloricoTv.setText(String.format("%.2f", tmb))

            }
        }

        binding.voltarBt.setOnClickListener { finish() }

        binding.calcularPesoIdealBt.setOnClickListener {

            var intent = Intent(this@GastoCalorico, PesoIdeal::class.java).apply{
                putExtra(Constants.EXTRA_DATA_PERSON, dados)
                putExtra(Constants.EXTRA_GASTO_CALORICO, tmb)
                putExtra(Constants.EXTRA_IMC, intent.getDoubleExtra(Constants.EXTRA_IMC,0.0))
                putExtra(Constants.EXTRA_CATEGORIA, intent.getStringExtra(Constants.EXTRA_CATEGORIA))
            }
            startActivity(intent)
        }
    }
}