package com.project.a65ddm_trabalho1.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MedicamentoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(medicamento: Medicamento)

    @Query("SELECT * FROM medicamentos")
    suspend fun listarMedicamentos(): List<Medicamento>

    @Delete
    suspend fun deletarMedicamento(medicamento: Medicamento)

    @Update
    suspend fun atualizarMedicamento(medicamento: Medicamento)
}
