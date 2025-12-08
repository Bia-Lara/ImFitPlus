package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.AdapterView
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.R // Importe o R do seu projeto
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.controller.MainController
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.ActivityPersonalDataBinding
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Constants
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.DataPerson

class PersonalData : BaseActivity() {
    private val binding: ActivityPersonalDataBinding by lazy {
        ActivityPersonalDataBinding.inflate(layoutInflater)
    }

    private val mainController: MainController by lazy {
        MainController(this)
    }
    lateinit var uiHandler: Handler
    private var nivelAtividadeFisica = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarIn.toolbar)

        initializeHandler()
        setupListeners()

        mainController.getUltimoUsuario()
    }

    private fun initializeHandler() {
        uiHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                if (msg.what == 3) {
                    val ultimoUsuario = msg.data.getParcelable<DataPerson>("ultimoUsuario")
                    ultimoUsuario?.let {
                        preencherCampos(it)
                    }
                }
            }
        }
    }

    private fun setupListeners() {
        binding.nivelAtividadeSr.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p: AdapterView<*>?, v: View?, pos: Int, id: Long) {
                    nivelAtividadeFisica = (v as TextView).text.toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        binding.calcularImcBt.setOnClickListener {
            processarCalculo()
        }
    }
    private fun processarCalculo() {
        val nome = binding.nomeEt.text.toString()
        val idade = binding.idadeEt.text.toString().toIntOrNull()
        val altura = binding.alturaEt.text.toString().toDoubleOrNull()
        val peso = binding.pesoEt.text.toString().toDoubleOrNull()
        val sexoId = binding.sexoRg.checkedRadioButtonId

        if (!validarCampos(nome, idade, altura, peso, sexoId)) {
            Toast.makeText(this, "Por favor, preencha todos os campos corretamente.", Toast.LENGTH_LONG).show()
            return
        }

        val radioButton = findViewById<RadioButton>(sexoId)
        val sexoSelecionado = radioButton.text.toString()

        val dadosPessoa = DataPerson(
            nome = nome,
            idade = idade!!,
            sexo = sexoSelecionado,
            altura = altura!!,
            peso = peso!!,
            nivel_atividade = nivelAtividadeFisica
        )

        mainController.inserirUsuario(dadosPessoa) { userId ->
            val intent = Intent(this@PersonalData, ImcResult::class.java).apply {
                putExtra(Constants.EXTRA_DATA_PERSON, dadosPessoa)
                putExtra(Constants.EXTRA_USER_ID, userId)
            }
            startActivity(intent)
        }
    }

    private fun preencherCampos(usuario: DataPerson) {
        binding.nomeEt.setText(usuario.nome)
        binding.idadeEt.setText(usuario.idade.toString())
        binding.alturaEt.setText(usuario.altura.toString())
        binding.pesoEt.setText(usuario.peso.toString())

        if (usuario.sexo == "Feminino") {
            binding.sexoRg.check(R.id.feminino_rb)
        } else {
            binding.sexoRg.check(R.id.masculino_rb)
        }

        val adapter = binding.nivelAtividadeSr.adapter
        for (i in 0 until adapter.count) {
            if (adapter.getItem(i).toString() == usuario.nivel_atividade) {
                binding.nivelAtividadeSr.setSelection(i)
                break
            }
        }
    }

    private fun validarCampos(nome: String, idade: Int?, altura: Double?, peso: Double?, sexoId: Int): Boolean {
        return when {
            nome.isBlank() -> false
            idade == null || idade <= 0 -> false
            altura == null || altura < 1.0 || altura > 2.5 -> false
            peso == null || peso <= 0 -> false
            sexoId == -1 -> false
            else -> true
        }
    }
}
