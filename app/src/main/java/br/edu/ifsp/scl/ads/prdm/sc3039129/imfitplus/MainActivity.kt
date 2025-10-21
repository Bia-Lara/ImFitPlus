package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var narl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        narl= registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){}

        binding.comecarBt.setOnClickListener {
            val intent = Intent(this@MainActivity, PersonalData::class.java)
            narl.launch(intent)
        }

    }
}