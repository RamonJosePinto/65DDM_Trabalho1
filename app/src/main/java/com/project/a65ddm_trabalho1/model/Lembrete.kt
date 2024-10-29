package com.project.a65ddm_trabalho1.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Calendar
import java.util.Date

@Parcelize
@Entity(tableName = "lembretes")
data class Lembrete(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val medicamentoId: Int,
    val dataLembrete: Long,
    val mensagem: String,
    val repeticao: String,
    val ativo: Boolean = true

): Parcelable{
    fun getCalendar(): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dataLembrete
        return calendar
    }
    fun getDate(): Date {
        return Date(dataLembrete)
    }

    companion object {
        fun fromCalendar(calendar: Calendar, medicamentoId: Int, mensagem: String, repeticao: String, ativo: Boolean): Lembrete {
            return Lembrete(
                medicamentoId = medicamentoId,
                dataLembrete = calendar.timeInMillis,
                mensagem = mensagem,
                repeticao = repeticao,
                ativo = ativo
            )
        }

        fun fromDate(date: Date, medicamentoId: Int, mensagem: String, repeticao: String, ativo: Boolean): Lembrete {
            return Lembrete(
                medicamentoId = medicamentoId,
                dataLembrete = date.time,
                mensagem = mensagem,
                repeticao = repeticao,
                ativo = ativo
            )
        }
    }
}
