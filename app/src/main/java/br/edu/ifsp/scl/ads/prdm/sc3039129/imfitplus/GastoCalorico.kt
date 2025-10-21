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
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.ActivityGastoCaloricoBinding

class GastoCalorico : AppCompatActivity() {
    private val binding: ActivityGastoCaloricoBinding by lazy{
        ActivityGastoCaloricoBinding.inflate(layoutInflater)
    }

    private lateinit var narl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        narl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){}

        binding.voltarBt.setOnClickListener { finish() }

        binding.calcularPesoIdealBt.setOnClickListener {
            var intent = Intent(this@GastoCalorico, PesoIdeal::class.java)
            narl.launch(intent)
        }
    }
}