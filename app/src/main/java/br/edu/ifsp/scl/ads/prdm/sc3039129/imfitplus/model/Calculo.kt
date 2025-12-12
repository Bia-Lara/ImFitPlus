package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime

@Parcelize
@Entity
data class Calculo(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val idUsuario: Int,
    val nome: String?,
    val imc: Double?,
    val categoriaImc: String?,
    var tmb: Double? = 0.0,
    var pesoIdeal: Double? = 0.0,
    var fcmax: Int? = 0,
    var zona_leve: Int? = 0,
    var zona_queima_gordura: Int? = 0,
    var zona_aerobica: Int? = 0,
    var zona_anaerobica: Int = 0,
    val dataHora: LocalDate = LocalDate.now()
) : Parcelable