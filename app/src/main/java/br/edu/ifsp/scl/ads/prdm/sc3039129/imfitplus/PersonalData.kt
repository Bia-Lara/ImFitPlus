package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.ActivityPersonalDataBinding
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Constants.EXTRA_DATA_PERSON
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.DataPerson

class PersonalData : AppCompatActivity() {
    private val binding: ActivityPersonalDataBinding by lazy {
        ActivityPersonalDataBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fun validateData(nome:String, idade:Int, altura:Double, peso:Double, sexo:Int): Boolean{
            if (nome.isEmpty()) { return false }

            if (idade == null || idade <= 0) { return false }

            if (altura < 1.0 || altura > 2.5) { return false }

            if (peso == null || peso <= 0) { return false }

            if (sexo == -1) { return false }

            return true
        }

        var nivelAtividadeFisica = ""
        binding.nivelAtividadeSr.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    nivelAtividadeFisica = (view as TextView).text.toString()

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        binding.calcularImcBt.setOnClickListener {

            val nome = binding.nomeEt.text.toString()
            val idade = binding.idadeEt.text.toString().toIntOrNull() ?: 0
            val altura = binding.alturaEt.text.toString().toDoubleOrNull() ?: 0.0
            val peso = binding.pesoEt.text.toString().toDoubleOrNull() ?: 0.0
            val sexo = binding.sexoRg.checkedRadioButtonId

            val validate= validateData(nome,idade,altura,peso,sexo)

            if(!validate){
                Toast.makeText(this, "Por favor, preencha todos os campos corretamente (altura em metros e peso em kg)", Toast.LENGTH_LONG).show()
            }else{
                if (sexo != -1) {
                    val radioButton = findViewById<RadioButton>(sexo)
                    val sexoSelecionado = radioButton.text.toString()

                    val dadosPessoa = DataPerson(
                        nome = nome,
                        idade = idade,
                        sexo = sexoSelecionado,
                        altura = altura,
                        peso = peso,
                        nivel_atividade = nivelAtividadeFisica
                    )

                    val intent = Intent(this@PersonalData, ImcResult::class.java).apply {
                        putExtra(EXTRA_DATA_PERSON, dadosPessoa)
                    }
                    startActivity(intent)
                }
            }
        }
    }
}