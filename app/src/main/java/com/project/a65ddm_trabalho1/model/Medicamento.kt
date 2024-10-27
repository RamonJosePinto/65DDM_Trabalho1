package com.project.a65ddm_trabalho1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicamentos")
data class Medicamento(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val dosagem: String,
    val fotoCaminho: String? = null  // Caminho da imagem no armazenamento
)
