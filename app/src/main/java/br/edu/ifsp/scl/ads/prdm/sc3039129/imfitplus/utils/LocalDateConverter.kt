package br.edu.ifsp.scl.ads.prdm.sc3039129.imfitplus.utils

import androidx.room.TypeConverter
import java.time.LocalDate

class LocalDateConverter {

    @TypeConverter
    fun fromString(value: String?): LocalDate? =
        value?.let { LocalDate.parse(it) }

    @TypeConverter
    fun dateToString(date: LocalDate?): String? =
        date?.toString()
}
