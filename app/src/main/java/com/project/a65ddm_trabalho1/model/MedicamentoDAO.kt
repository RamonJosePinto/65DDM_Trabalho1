package com.project.a65ddm_trabalho1.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MedicamentoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(medicamento: Medicamento)

    @Query("SELECT * FROM medicamentos")
    suspend fun listarMedicamentos(): List<Medicamento>
}
