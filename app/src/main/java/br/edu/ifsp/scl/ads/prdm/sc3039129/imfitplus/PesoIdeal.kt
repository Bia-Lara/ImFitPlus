package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.ActivityMainBinding
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.ActivityPesoIdealBinding

class PesoIdeal : AppCompatActivity() {
    private val binding: ActivityPesoIdealBinding by lazy{
        ActivityPesoIdealBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.voltarBt.setOnClickListener {
            finish()
        }
    }
}