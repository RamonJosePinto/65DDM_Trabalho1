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
    val medicamentoId: Int, // Chave estrangeira associando o lembrete a um medicamento
    val dataLembrete: Long, // Timestamp ou outro formato para armazenar a data e hora do lembrete
    val mensagem: String, // Mensagem associada ao lembrete
    val repeticao: String, // Tipo de repetição (todos os dias, dia específico, etc.)
    val ativo: Boolean = true // Novo atributo para gerenciar a ativação/desativação de lembretes

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
        // Função estática para converter Calendar para Long (timestamp)
        fun fromCalendar(calendar: Calendar, medicamentoId: Int, mensagem: String, repeticao: String, ativo: Boolean): Lembrete {
            return Lembrete(
                medicamentoId = medicamentoId,
                dataLembrete = calendar.timeInMillis,  // Aqui converte para Long
                mensagem = mensagem,
                repeticao = repeticao,
                ativo = ativo
            )
        }

        // Função estática para converter Date para Long (timestamp)
        fun fromDate(date: Date, medicamentoId: Int, mensagem: String, repeticao: String, ativo: Boolean): Lembrete {
            return Lembrete(
                medicamentoId = medicamentoId,
                dataLembrete = date.time,  // Aqui converte para Long
                mensagem = mensagem,
                repeticao = repeticao,
                ativo = ativo
            )
        }
    }
}
