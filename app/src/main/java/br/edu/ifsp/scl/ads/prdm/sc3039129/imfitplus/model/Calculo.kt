package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Calculo(
    @PrimaryKey(autoGenerate = true) var id: Int? = -1,
    val idUsuario: Int,
    val imc: Double?,
    val categoriaImc: String?,
    val tmb: Double?,
    val pesoIdeal: Double?,
    val dataHora: Long
) : Parcelable