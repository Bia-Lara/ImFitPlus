package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.R
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.databinding.TileHistoryBinding
import br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model.Calculo

class HistoryAdapter(
    context: Context,
    private val historyList: MutableList<Calculo>
): ArrayAdapter<Calculo>(context, R.layout.tile_history, historyList)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val history = historyList[position]

        var historyTileView = convertView
        if (historyTileView == null) {
            TileHistoryBinding.inflate(
                context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent,
                false
            ).apply {
                historyTileView = root
                val tileHistoryHolder = TileHistoryViewHolder(
                    nameTv,
                    imcTv,
                    categoriaImcTv,
                    tmbTv,
                    pesoIdealTv,
                    dataTv
                )
                historyTileView.tag = tileHistoryHolder
            }
        }

        val tileHistoryHolder = historyTileView?.tag as TileHistoryViewHolder
        tileHistoryHolder.nameTv.text = history.nome
        tileHistoryHolder.imcTv.text = history.imc.toString()
        tileHistoryHolder.categoriaImcTv.text = history.categoriaImc
        tileHistoryHolder.tmbTv.text = history.tmb.toString()
        tileHistoryHolder.pesoIdealTv.text = history.pesoIdeal.toString()
        tileHistoryHolder.dataTv.text = history.dataHora.toString()

        return historyTileView
    }

    private data class TileHistoryViewHolder(
        val nameTv: TextView,
        val imcTv: TextView,
        val categoriaImcTv: TextView,
        val tmbTv: TextView,
        val pesoIdealTv: TextView,
        val dataTv: TextView
    )

}