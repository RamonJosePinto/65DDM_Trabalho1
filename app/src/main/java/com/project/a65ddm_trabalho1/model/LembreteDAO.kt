package com.project.a65ddm_trabalho1.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LembreteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirLembrete(lembrete: Lembrete)

    @Query("SELECT * FROM lembretes WHERE medicamentoId = :medicamentoId")
    suspend fun listarLembretesPorMedicamento(medicamentoId: Int): List<Lembrete>

    @Query("SELECT * FROM lembretes")
    suspend fun listarTodosLembretes(): List<Lembrete>

    @Query("DELETE FROM lembretes WHERE id = :lembreteId")
    suspend fun deletarLembrete(lembreteId: Int)
}
