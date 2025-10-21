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
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.ActivityPersonalDataBinding

class PersonalData : AppCompatActivity() {
    private val binding: ActivityPersonalDataBinding by lazy{
        ActivityPersonalDataBinding.inflate(layoutInflater)
    }

    private lateinit var narl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        narl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            result->
            if(result.resultCode == RESULT_OK){
            }
        }

        binding.calcularImcBt.setOnClickListener {
            val intent = Intent(this@PersonalData, ImcResult::class.java)
            narl.launch(intent)

        }
    }
}