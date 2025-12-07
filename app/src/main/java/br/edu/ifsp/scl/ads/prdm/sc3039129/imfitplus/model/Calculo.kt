package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate

@Parcelize
@Entity
data class Calculo(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val idUsuario: Int,
    val nome: String?,
    val imc: Double?,
    val categoriaImc: String?,
    val tmb: Double? = 0.0,
    val pesoIdeal: Double? = 0.0,
    val dataHora: LocalDate = LocalDate.now()
) : Parcelable