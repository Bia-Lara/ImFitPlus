package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.ActivityMainBinding
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.ActivityPesoIdealBinding
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Constants.EXTRA_DATA_PERSON
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

        dados.let{
            with(binding){
                val pesoIdeal= calculatePesoIdeal(it?.altura!!)
                resultadoPesoIdealTv.setText(String.format("%.2f", pesoIdeal))
            }
        }

        binding.voltarBt.setOnClickListener {
            finish()
        }
    }
}