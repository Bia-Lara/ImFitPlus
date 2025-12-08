package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.ui

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.adapter.HistoryAdapter
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.controller.MainController
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.ActivityHistoricoBinding
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Calculo

class HistoricoActivity : BaseActivity() {
    private val binding: ActivityHistoricoBinding by lazy {
        ActivityHistoricoBinding.inflate(layoutInflater)
    }

    private val mainController: MainController by lazy {
        MainController(this)
    }
    private val historyList: MutableList<Calculo> = mutableListOf()

    private val historyAdapter: HistoryAdapter by lazy {
        HistoryAdapter(this, historyList)
    }
    companion object {
        const val BUSCAR_CALCULOS = 1
        const val ATUALIZAR_LISTA_DE_CALCULOS = 2
    }

    val uiHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                BUSCAR_CALCULOS -> {
                    mainController.buscarCalculos()
                }

                ATUALIZAR_LISTA_DE_CALCULOS -> {
                    val listaRecebida = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        msg.data.getParcelableArrayList("listaCalculos", Calculo::class.java)
                    } else {
                        msg.data.getParcelableArrayList<Calculo>("listaCalculos")
                    }

                    historyList.clear()
                    listaRecebida?.forEach { historyList.add(it) }
                    historyAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.historyList.adapter = historyAdapter

        iniciarBuscaDeHistorico()

        binding.voltarBt.setOnClickListener { finish() }
    }

    private fun iniciarBuscaDeHistorico() {
        uiHandler.sendMessage(uiHandler.obtainMessage(BUSCAR_CALCULOS))
    }
}
