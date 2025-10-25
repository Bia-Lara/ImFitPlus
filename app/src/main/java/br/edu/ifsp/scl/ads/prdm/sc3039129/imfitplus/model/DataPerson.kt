package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataPerson(
    var nome: String = "",
    var idade: Int = 1,
    var sexo: String = "",
    var altura: Double = 0.0,
    var peso: Double = 0.0,
    var nivel_atividade: String=""

): Parcelable