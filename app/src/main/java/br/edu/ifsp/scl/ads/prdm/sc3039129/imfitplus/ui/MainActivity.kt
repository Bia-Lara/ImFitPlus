package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.ui.PersonalData
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.R
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private val binding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var carl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.comecarBt.setOnClickListener {
            val intent = Intent(this@MainActivity, PersonalData::class.java)
            startActivity(intent)
        }

        setSupportActionBar(binding.toolbarIn.toolbar)


    }


}