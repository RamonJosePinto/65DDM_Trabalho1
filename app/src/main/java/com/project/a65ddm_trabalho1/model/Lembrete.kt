package com.project.a65ddm_trabalho1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lembretes")
data class Lembrete(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val medicamentoId: Int, // Chave estrangeira associando o lembrete a um medicamento
    val dataLembrete: Long, // Timestamp ou outro formato para armazenar a data e hora do lembrete
    val mensagem: String, // Mensagem associada ao lembrete
    val repeticao: String // Tipo de repetição (todos os dias, dia específico, etc.)
)
